package com.killdongmu.Hanghae99Week5BackEnd.dto.response;


import com.killdongmu.Hanghae99Week5BackEnd.entity.Comments;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Builder
@AllArgsConstructor
public class BoardResponseDto {

    private Long board_id;

    private String title;

    private String file;

    private String content;

    private List<Comments> commentList;

    private String username;

    private List<String> heartedUsernameList;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
