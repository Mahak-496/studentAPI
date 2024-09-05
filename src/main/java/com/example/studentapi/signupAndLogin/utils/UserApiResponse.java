package com.example.studentapi.signupAndLogin.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserApiResponse {
    private String message;
    private int statusCode;
    private Object data;
    private String token;
}
