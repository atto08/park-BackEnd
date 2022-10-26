package com.killdongmu.Hanghae99Week5BackEnd.controller;

import com.killdongmu.Hanghae99Week5BackEnd.dto.request.CommentRequestDto;
import com.killdongmu.Hanghae99Week5BackEnd.security.MemberDetails;
import com.killdongmu.Hanghae99Week5BackEnd.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class CommentController {

    private final CommentService commentService;

    @GetMapping(value = "/{board-id}/comments")
    public ResponseEntity<?> commentList(@PathVariable(name = "board-id") Long boardId) {
        return commentService.findCommentList(boardId);
    }

    @GetMapping(value = "/comments/{comment-id}")
    public ResponseEntity<?> commentInfo(@PathVariable(name = "comment-id") Long commentId) {
        return commentService.findComment(commentId);
    }

    @PostMapping(value ="/{board-id}/comments/create")
    public ResponseEntity<?> createComment(@RequestBody CommentRequestDto commentRequestDto,
                                           @PathVariable(name = "board-id") Long boardId,
                                           @AuthenticationPrincipal MemberDetails memberDetails) {
        return commentService.createComment(commentRequestDto, boardId, memberDetails.getMember());
    }

    @PutMapping(value = "/comments/update/{comment-id}")
    public ResponseEntity<?> updateComment(@RequestBody CommentRequestDto commentRequestDto,
                                           @PathVariable(name = "comment-id") Long commentId,
                                           @AuthenticationPrincipal MemberDetails memberDetails) {
        return commentService.updateComment(commentRequestDto, commentId, memberDetails.getMember());
    }

    @DeleteMapping(value = "/comments/delete/{comment-id}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "comment-id") Long commentId,
                                           @AuthenticationPrincipal MemberDetails memberDetails) {
        return commentService.deleteComment(commentId, memberDetails.getMember());
    }
}
