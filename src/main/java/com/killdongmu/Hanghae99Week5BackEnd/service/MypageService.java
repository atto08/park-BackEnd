package com.killdongmu.Hanghae99Week5BackEnd.service;

import com.killdongmu.Hanghae99Week5BackEnd.dto.response.MypageResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Boards;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Comments;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Hearts;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Members;
import com.killdongmu.Hanghae99Week5BackEnd.repository.BoardRepository;
import com.killdongmu.Hanghae99Week5BackEnd.repository.CommentRepository;
import com.killdongmu.Hanghae99Week5BackEnd.repository.HeartRepository;
import com.killdongmu.Hanghae99Week5BackEnd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final HeartRepository heartRepository;

    public ResponseEntity<?> findMemberInfo(String username) {

        Members member = memberRepository.findByUsername(username).orElseThrow(
                ()->new UsernameNotFoundException(username+"을 찾을 수 없습니다")
        );

        List<Boards> boardList = boardRepository.findAllByMember(member);
        List<Comments> commentList = commentRepository.findAllByMember(member);
        List<Hearts> heartList = heartRepository.findAllByMember(member);

        List<String> boardListTemp = new ArrayList<>();
        List<String> commentListTemp = new ArrayList<>();
        List<String> heartListTemp = new ArrayList<>();

        for(Boards board : boardList) {
           boardListTemp.add(board.getTitle());
        }

        for(Comments comment : commentList) {
            commentListTemp.add(comment.getComment());
        }

        for(Hearts heart : heartList) {
            heartListTemp.add(heart.getBoard().getTitle());
        }



        MypageResponseDto mypage = MypageResponseDto.builder().
                username(member.getUsername()).
                memberId(member.getMemberId()).
                email(member.getEmail()).
                writtenBoardList(boardListTemp).
                writtenCommentList(commentListTemp).
                heartList(heartListTemp).
                build();

        return new ResponseEntity<>(mypage, HttpStatus.OK);









    }







}
