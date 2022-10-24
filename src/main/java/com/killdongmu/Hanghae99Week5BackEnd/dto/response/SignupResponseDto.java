package com.killdongmu.Hanghae99Week5BackEnd.dto.response;

import com.killdongmu.Hanghae99Week5BackEnd.util.Authority;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupResponseDto {

    private String username;

    private Authority authority;
}
