package com.example.studentapi.exceptions;

import com.example.studentapi.utils.ApiResponse;
import com.example.studentapi.utils.ResponseSender;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message("You do not have permission to perform this action.")
                .statusCode(HttpStatus.FORBIDDEN.value())
                .build();
        return ResponseSender.send(apiResponse);
    }
}
