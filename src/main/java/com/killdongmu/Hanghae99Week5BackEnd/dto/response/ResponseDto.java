package com.killdongmu.Hanghae99Week5BackEnd.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {

    private boolean success;
    private T data;
    private Error error;

    // 제너릭 메소드
    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(true, data, null);
    }

    public static <T> ResponseDto<T> fail(String code, String msg) {
        return new ResponseDto<>(false, null, new Error(code, msg));
    }

    @Getter
    @AllArgsConstructor
    static class Error {
        private String code;
        private String msg;
    }
}
