package com.example.studentapi.signupAndLogin.service;

import com.example.studentapi.signupAndLogin.configuration.JwtService;
import com.example.studentapi.signupAndLogin.dto.mapper.UserMapper;
import com.example.studentapi.signupAndLogin.dto.request.UserRequest;
import com.example.studentapi.signupAndLogin.dto.response.UserResponse;
import com.example.studentapi.signupAndLogin.entity.PasswordResetToken;
import com.example.studentapi.signupAndLogin.entity.User;
import com.example.studentapi.signupAndLogin.repository.PasswordResetTokenRepository;
import com.example.studentapi.signupAndLogin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtHelper;

    @Autowired
    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtHelper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
    }
    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    public boolean isEmailRegistered(String email) {
        return userRepository.existsByEmail(email);
    }
    @Override
    public UserResponse registerUser(UserRequest userRequest) {
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }

        User user = UserMapper.toUserEntity(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User registeredUser = userRepository.save(user);
        return UserMapper.toResponse(registeredUser);
    }

    @Override
    public void resetPassword(String email, String newPassword) throws Exception {
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new Exception("User not found.");
        }
    }
    @Override
    public UserResponse loginUser(UserRequest userRequest) throws AuthenticationException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword())
            );
            if (authentication.isAuthenticated()) {
                String token = jwtHelper.generateToken(userRequest.getEmail());

                User user = userRepository.findByEmail(userRequest.getEmail())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                user.setToken(token);
                userRepository.save(user);
                return UserMapper.toResponse(user);
            } else {
                throw new RuntimeException("Invalid email or password");
            }
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Invalid email or password");
        }
    }



    public String generateToken(){
        UUID uuid=UUID.randomUUID();
        String token=uuid.toString();
        return token;

    }



    public String createPasswordResetToken(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            String token = generateToken();
            PasswordResetToken resetToken = new PasswordResetToken(token, user.get());
            tokenRepository.save(resetToken);
            return token;
        }
        throw new RuntimeException("User not found");
    }



    public String getEmailByToken(String token) throws Exception {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new Exception("Invalid or expired token"));
        return resetToken.getUser().getEmail();
    }

    public void resetPasswordByToken(String token, String newPassword) throws Exception {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new Exception("Invalid or expired token"));

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.delete(resetToken);
    }



}
