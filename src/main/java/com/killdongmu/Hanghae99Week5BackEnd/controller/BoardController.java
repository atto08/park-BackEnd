package com.killdongmu.Hanghae99Week5BackEnd.controller;

import com.killdongmu.Hanghae99Week5BackEnd.dto.request.BoardRequestDto;
import com.killdongmu.Hanghae99Week5BackEnd.security.MemberDetails;
import com.killdongmu.Hanghae99Week5BackEnd.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping(value = "/list")
    public ResponseEntity<?> boardList(){
        return boardService.findBoardList();
    }

    // 게시판 전체 페이징 조회 GET /boards/pager?page=3&size=10  + Board Total Count
    @GetMapping ("/list/pager")
    public ResponseEntity<?> getBoardPagerList(
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        int pageTemp = page - 1;
        return boardService.findBoardPageList(pageTemp,size);
    }

    @GetMapping(value = "/detail/{board-id}")
    public ResponseEntity<?> boardInfo(@PathVariable(name = "board-id") Long boardId){
        return boardService.findBoard(boardId);
    }

    @PostMapping(value ="/create")
    public ResponseEntity<?> createBoard(@ModelAttribute BoardRequestDto boardRequestDto,
                                         @AuthenticationPrincipal MemberDetails memberDetails) throws IOException {
        return boardService.createBoard(boardRequestDto, memberDetails.getMember());
    }

    @PutMapping(value ="/update/{board-id}")
    public ResponseEntity<?> updateBoard(@RequestBody BoardRequestDto boardRequestDto,
                            @PathVariable(name = "board-id") Long boardId,
                            @AuthenticationPrincipal MemberDetails memberDetails) {
        return boardService.updateBoard(boardRequestDto, boardId, memberDetails.getMember());
    }

    @DeleteMapping(value ="/delete/{board-id}")
    public ResponseEntity<?> deleteBoard(@PathVariable(name = "board-id") Long boardId,
                                         @AuthenticationPrincipal MemberDetails memberDetails) {
        return boardService.deleteBoard(boardId, memberDetails.getMember());
    }
}