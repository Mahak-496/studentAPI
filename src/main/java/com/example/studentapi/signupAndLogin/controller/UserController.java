package com.example.studentapi.signupAndLogin.controller;

import com.example.studentapi.signupAndLogin.service.UserService;
import com.example.studentapi.signupAndLogin.dto.request.UserRequest;
import com.example.studentapi.signupAndLogin.dto.response.UserResponse;
import com.example.studentapi.utils.ApiResponse;
import com.example.studentapi.utils.ResponseSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/api/auth/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Validated UserRequest userRequest) {
        try {
            UserResponse response = userService.registerUser(userRequest);
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("User registered successfully")
                    .data(response)
                    .statusCode(HttpStatus.CREATED.value())
                    .build();
            return ResponseSender.send(apiResponse);
        } catch (Exception e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message(e.getMessage())
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
            return ResponseSender.send(apiResponse);
        }
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody UserRequest userRequest) {
        try {
            UserResponse response = userService.loginUser(userRequest);
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Login successful")
                    .data(response)
                    .statusCode(HttpStatus.OK.value())
                    .build();
            return ResponseEntity.ok(apiResponse);
        }  catch (Exception e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("An error occurred")
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message("An unexpected error occurred: " + e.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return ResponseSender.send(apiResponse);
    }

}



