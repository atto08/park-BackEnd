package com.killdongmu.Hanghae99Week5BackEnd.service;

import com.killdongmu.Hanghae99Week5BackEnd.dto.request.CommentRequestDto;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Boards;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Comments;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Members;
import com.killdongmu.Hanghae99Week5BackEnd.repository.BoardRepository;
import com.killdongmu.Hanghae99Week5BackEnd.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    public ResponseEntity<?> findCommentList(Long boardId) {

        Boards board = boardRepository.findById(boardId).orElseThrow(RuntimeException::new);

        List<Comments> commentList = commentRepository.findCommentsByBoardOrderByCreatedAtDesc(board);

        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    public ResponseEntity<?> findComment(Long commentId) {

        Comments comment = commentRepository.findById(commentId).orElse(null);

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    public ResponseEntity<?> createComment(CommentRequestDto commentRequestDto, Long boardId, Members members) {

        Comments comment = Comments.builder().
                comment(commentRequestDto.getComment()).
                board(boardRepository.findById(boardId).orElseThrow(RuntimeException::new)).
                member(members).
                build();

        commentRepository.save(comment);

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> updateComment(CommentRequestDto commentRequestDto, Long commentId, Members members) {

        Comments comment = commentRepository.findById(commentId).orElseThrow((NullPointerException::new));

        if(!comment.getMember().getMember_id().equals(members.getMember_id()))
            throw new RuntimeException("작성자와 로그인 사용자가 일치하지 않습니다.");

        comment.updateComment(commentRequestDto.getComment());

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteComment(Long commentId, Members members) {

        Comments comment = commentRepository.findById(commentId).orElseThrow((NullPointerException::new));

        if(!comment.getMember().getMember_id().equals(members.getMember_id()))
            throw new RuntimeException("작성자와 로그인 사용자가 일치하지 않습니다.");

        commentRepository.deleteById(commentId);

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
}
