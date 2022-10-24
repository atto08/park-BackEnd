package com.killdongmu.Hanghae99Week5BackEnd.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class SignupRequestDto {

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z]).{4,12}", message = "닉네임은 최소 4자 이상, 12자 이하 알파벳 대소문자(a-z, A-Z), 숫자(0-9)로 구성됩니다.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z]).{4,32}", message = "비밀번호는 최소 4자 이상, 32자 이하 알파벳 소문자(a-z), 숫자(0-9)로 구성됩니다.")
    private String password;

    @NotBlank
    private String passwordConfirm;

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;
}
