package com.example.studentapi.signupAndLogin.utils;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class UserResponseSender {
    public static ResponseEntity<Object> send(UserApiResponse response) {
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
}
