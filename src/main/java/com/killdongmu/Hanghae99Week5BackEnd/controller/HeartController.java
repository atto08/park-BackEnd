package com.killdongmu.Hanghae99Week5BackEnd.controller;

import com.killdongmu.Hanghae99Week5BackEnd.security.MemberDetails;
import com.killdongmu.Hanghae99Week5BackEnd.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;

    @PostMapping(value = "/api/hearts/{board-id}", consumes = "application/json")
    public void clickHeart(@PathVariable("board-id") Long boardId,
                           @AuthenticationPrincipal MemberDetails memberDetails) {
        heartService.clickHeart(boardId, memberDetails.getMember());
    }



}
