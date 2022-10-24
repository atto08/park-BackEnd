package com.killdongmu.Hanghae99Week5BackEnd.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {

    @NotBlank(message = "댓글을 입력해주세요.")
    private String comment;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
