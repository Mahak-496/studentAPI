package com.example.studentapi.signupAndLogin.dto.response;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private int id;
    private String username;
    private String role;
    private String email;
    private String token;
}
