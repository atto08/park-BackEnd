package com.killdongmu.Hanghae99Week5BackEnd.service;

import com.killdongmu.Hanghae99Week5BackEnd.dto.request.BoardRequestDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.BoardListResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.response.BoardResponseDto;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Boards;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Comments;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Hearts;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Members;
import com.killdongmu.Hanghae99Week5BackEnd.repository.BoardRepository;
import com.killdongmu.Hanghae99Week5BackEnd.repository.CommentRepository;
import com.killdongmu.Hanghae99Week5BackEnd.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final CommentRepository commentRepository;

    private final HeartRepository heartRepository;

    public ResponseEntity<?> findBoardList() {

        List<Boards> boardList = boardRepository.findAllByOrderByCreatedAtDesc();

        return new ResponseEntity<>(boardList, HttpStatus.OK);

    }

    public ResponseEntity<?> findBoard(Long boardId) {

        Boards board = boardRepository.findById(boardId).orElseThrow(RuntimeException::new);

        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    public ResponseEntity<?> createBoard(BoardRequestDto boardRequestDto, Members members) {

        Boards board = Boards.builder().
                title(boardRequestDto.getTitle()).
                content(boardRequestDto.getContent()).
                member(members).
                build();

        boardRepository.save(board);

        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> updateBoard(BoardRequestDto boardRequestDto, Long boardId, Members members) {

        // Boards board = boardRepository.findById(boardId).orElseThrow(() -> new NullPointerException());
        Boards board = boardRepository.findById(boardId).orElseThrow(NullPointerException::new);

        if(!board.getMember().getMember_id().equals(members.getMember_id()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        board.updateBoard(boardRequestDto.getTitle(), boardRequestDto.getContent());

        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteBoard(Long boardId, Members members) {

        // 게시글 삭제할 때 해당 게시글의 댓글, 좋아요 삭제
        Boards board = boardRepository.findById(boardId).orElseThrow(RuntimeException::new);
        List<Comments> commentList = commentRepository.findAllByBoard(board);
        List<Hearts> heartList = heartRepository.findAllByBoard(board);

        if(!board.getMember().getMember_id().equals(members.getMember_id()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (commentList.size() > 0) {
            commentRepository.deleteByBoard(board);
        }

        if (heartList.size() > 0) {
            heartRepository.deleteByBoard(board);
        }

        boardRepository.deleteById(boardId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}