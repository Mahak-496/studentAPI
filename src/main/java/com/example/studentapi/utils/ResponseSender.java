package com.example.studentapi.utils;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public abstract class ResponseSender {
    public static ResponseEntity<Object> send(ApiResponse response) {
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
}

