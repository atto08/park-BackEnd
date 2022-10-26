package com.killdongmu.Hanghae99Week5BackEnd.controller;


import com.killdongmu.Hanghae99Week5BackEnd.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/mypage")
@RestController
public class MypageController {

    private final MypageService mypageService;


    @GetMapping("/{username}")
    public ResponseEntity<?> findMemberInfo(@PathVariable String username) {
        return mypageService.findMemberInfo(username);
    }
}