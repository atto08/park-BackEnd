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
public class BoardListResponseDto {

    private Long boardId;

    private String title;

    private String content;

    private Long countComment;

    private Long countHeart;

    private String username;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
