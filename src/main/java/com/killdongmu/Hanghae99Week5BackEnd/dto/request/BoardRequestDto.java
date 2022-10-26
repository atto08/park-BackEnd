package com.killdongmu.Hanghae99Week5BackEnd.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
// 갓진석 왈: No ==> 쓰지마
@Getter
@AllArgsConstructor
public class BoardRequestDto {

    private MultipartFile file;

    @NotBlank(message = "게시글 제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "게시글 내용을 입력해주세요.")
    private String content;
}
