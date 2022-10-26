package com.killdongmu.Hanghae99Week5BackEnd.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

    private Long commentId;

    private String comment;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

}
