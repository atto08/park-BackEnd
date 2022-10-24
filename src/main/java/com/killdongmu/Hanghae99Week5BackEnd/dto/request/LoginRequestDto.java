package com.killdongmu.Hanghae99Week5BackEnd.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class LoginRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    // Security 인증용 토큰 만들기
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken( username , password );
    }
}
