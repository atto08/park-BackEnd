package com.killdongmu.Hanghae99Week5BackEnd.service;

import com.killdongmu.Hanghae99Week5BackEnd.dto.TokenDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.request.LoginRequestDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.request.SignupRequestDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.request.TokenRequestDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.ResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Members;
import com.killdongmu.Hanghae99Week5BackEnd.repository.MemberRepository;
import com.killdongmu.Hanghae99Week5BackEnd.repository.RefreshTokenRepository;
import com.killdongmu.Hanghae99Week5BackEnd.security.JwtFilter;
import com.killdongmu.Hanghae99Week5BackEnd.security.MemberDetails;
import com.killdongmu.Hanghae99Week5BackEnd.security.RefreshToken;
import com.killdongmu.Hanghae99Week5BackEnd.security.TokenProvider;
import com.killdongmu.Hanghae99Week5BackEnd.util.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    // UserDetailsService - 사용자별 데이터를 로드하는 핵심 인터페이스

    private final MemberRepository memberRepository;

    // 메모리 인증, LDAP 인증, JDBC 기반 인증, UserDetailsService 추가 및 AuthenticationProvider 추가를 쉽게 구축
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    // 암호 인코딩을 위한 서비스 인터페이스. 기본 구현은 BCryptPasswordEncoder
    private final PasswordEncoder passwordEncoder;

    // JWT 제공자
    private final TokenProvider tokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    // 사용자 이름 기반으로 사용자 찾기 - UserDetailsService 기본
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Members member = memberRepository
                .findByUsername(username)
                .orElseThrow(
                        ()->new UsernameNotFoundException(username+"을 찾을 수 없습니다")
                );

        return new MemberDetails(member);
    }

    @Transactional
    public ResponseEntity<?> createMember(SignupRequestDto signupRequestDto) {

        if(memberRepository.existsByUsername(signupRequestDto.getUsername())) {
            throw new RuntimeException("이미 존재하는 아이디입니다");
        }

        if(!signupRequestDto.getPassword().equals(signupRequestDto.getPasswordConfirm())) {
            throw new RuntimeException("비밀번호와 비밀번호 확인이 일치하지 않습니다");
        }
        Members member = new Members(
                signupRequestDto.getUsername(),
                passwordEncoder.encode(signupRequestDto.getPassword()),
                signupRequestDto.getEmail(),
                Authority.ROLE_USER
        );

        return ResponseEntity.ok(memberRepository.save(member));
    }

    @Transactional
    public ResponseEntity<?> loginMember(LoginRequestDto loginRequestDto) {

        // 받아온 걸로 Security 인증용 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginRequestDto.toAuthentication();

        // 검증
        // (Security Depth : SecurityContextHolder > Context > Authentication > UsernamePasswordAuthenticationToken > MemberDetails)
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        if(!memberRepository.existsByUsername(loginRequestDto.getUsername())){
            throw new RuntimeException("존재하지 않는 아이디입니다.");
        }

        Members member = memberRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(RuntimeException::new);

        // 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // refresh 토큰 db 저장
        RefreshToken refreshToken = RefreshToken.builder()
                // Key 검증된 유저 이름
                .key(authentication.getName())
                // value 문자열로 된 리스레쉬 토큰
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);
        // refresh 토큰 db 저장 끝

        // 클라이언트 발급용 토큰 헤더에 삼입
        HttpHeaders httpHeaders = new HttpHeaders();

        // 규칙인 Authorization 필드 만들고 Jwt 토큰 value에 Bearer 붙이고, 위에서 생성한 토큰 삽입
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, JwtFilter.BEARER_PREFIX + tokenDto.getAccessToken());
        httpHeaders.add("RefreshToken", tokenDto.getRefreshToken());
        httpHeaders.add("username",member.getUsername());

        System.out.println(httpHeaders.get(JwtFilter.AUTHORIZATION_HEADER));

        System.out.println(httpHeaders.get("RefreshToken"));

        System.out.println("login 성공");
        // 토큰 발급
        return new ResponseEntity<>(ResponseDto.success(member), httpHeaders, HttpStatus.OK);
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // refresh token 확인
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException(("Refresh Token이 유효하지 않습니다"));
        }

        // accesss token에서 member id 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 저장소에서 member id를 기반으로 refresh token 값 가져옴. UsernamePasswordAuthenticationToken.getName(Principal.getName())
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(()->new RuntimeException("로그아웃 된 사용자입니다"));

        // refresh token 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다");
        }

        // 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 새로 발급한 refresh 토큰 정보 db 업데이트
        RefreshToken refreshRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(refreshRefreshToken);

        // 새로 토큰 발급했으니 클라이언트한테 돌려준다
        return tokenDto;
    }
}
