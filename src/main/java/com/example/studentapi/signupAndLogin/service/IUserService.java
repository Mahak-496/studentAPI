package com.example.studentapi.signupAndLogin.service;

import com.example.studentapi.signupAndLogin.dto.request.UserRequest;
import com.example.studentapi.signupAndLogin.dto.response.UserResponse;
import com.example.studentapi.signupAndLogin.entity.User;

import javax.naming.AuthenticationException;

public interface IUserService {
    UserResponse registerUser(UserRequest userRequest);

    void resetPassword(String email, String newPassword) throws Exception;

    UserResponse loginUser(UserRequest userRequest) throws AuthenticationException;
}
