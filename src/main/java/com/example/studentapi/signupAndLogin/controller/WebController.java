//package com.example.studentapi.signupAndLogin.controller;
//
//import com.example.studentapi.signupAndLogin.dto.request.UserRequest;
//import com.example.studentapi.signupAndLogin.dto.response.UserResponse;
//import com.example.studentapi.signupAndLogin.service.UserService;
//import com.example.studentapi.utils.ApiResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.regex.Pattern;
//
//@Controller
//public class WebController {
//
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private RestTemplate restTemplate;
//    @Autowired
//    private JavaMailSender mailSender;
//
//    private static final String BASE_API_URL = "http://localhost:9090/api/auth";
//
//
//    @GetMapping("/auth/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("userRequest", new UserRequest());
//        return "register";
//    }
//
//    @PostMapping("/auth/register")
//    public String registerUser(UserRequest userRequest, Model model) {
//        try {
//            if (!isValidEmail(userRequest.getEmail())) {
//                model.addAttribute("error", "Invalid email address.");
//                return "register";
//            }
//            UserResponse response = userService.registerUser(userRequest);
//            model.addAttribute("message", "User registered successfully");
//            return "login";
//        } catch (Exception e) {
//            model.addAttribute("error", e.getMessage());
//            return "register";
//        }
//    }
//
//    @GetMapping("/auth/login")
//    public String showLoginForm(Model model) {
//        model.addAttribute("userRequest", new UserRequest());
//        return "login";
//    }
//
//    @PostMapping("/auth/login")
//    public String loginUser(UserRequest userRequest, Model model) {
//        try {
//            String url = BASE_API_URL + "/login";
//            ResponseEntity<ApiResponse> response = restTemplate.postForEntity(url, userRequest, ApiResponse.class);
//            model.addAttribute("user", response.getBody().getData());
//            model.addAttribute("message", response.getBody().getMessage());
//            return "home";
//        } catch (Exception e) {
//            model.addAttribute("error", e.getMessage());
//            return "login";
//        }
//    }
//
//    @GetMapping("/auth/home")
//    public String home(Model model) {
//        return "home";
//    }
//    @GetMapping("/auth/forgot-password")
//    public String showForgotPasswordForm(Model model) {
//        model.addAttribute("email", "");
//        return "forgot-password";
//    }
//
//    @PostMapping("/auth/forgot-password")
//    public String handleForgotPassword(@RequestParam String email, Model model) {
//        try {
//            if (!isValidEmail(email)) {
//                model.addAttribute("error", "Invalid email address.");
//                return "forgot-password";
//            }
//
//            if (!userService.isEmailRegistered(email)) {
//                model.addAttribute("error", "No account found with that email address.");
//                return "forgot-password";
//            }
//
//            String resetLink = "http://localhost:9090/auth/reset-password?email=" + email;
//
//            sendPasswordResetEmail(email, resetLink);
//
//            model.addAttribute("message", "Password reset email sent successfully.");
//            return "login";
//        } catch (Exception e) {
//            model.addAttribute("error", "Authentication failed: " + e.getMessage());
//            return "forgot-password";
//        }
//    }
//    private void sendPasswordResetEmail(String to, String resetLink) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject("Password Reset Request");
//        message.setText("To reset your password, please click the following link: " + resetLink);
//        mailSender.send(message);
//    }
//
//
//
//@GetMapping("/auth/reset-password")
//public String showResetPasswordForm(@RequestParam("email") String email, Model model) {
//    model.addAttribute("email", email);
//    return "reset-Password";
//}
//
//
//
//    @PostMapping("/auth/reset-password")
//    public String resetPassword(@RequestParam("email") String email,
//                                @RequestParam("newPassword") String newPassword,
//                                Model model) {
//        try {
//            userService.resetPassword(email, newPassword);
//            model.addAttribute("message", "Password reset successfully.");
//            return "login";
//        } catch (Exception e) {
//            model.addAttribute("error", "Failed to reset password: " + e.getMessage());
//            return "reset-Password";
//        }
//    }
//
//
//
//
//
//    private boolean isValidEmail(String email) {
//        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
//        Pattern pattern = Pattern.compile(emailRegex);
//        return pattern.matcher(email).matches();
//    }
//}
//
//




package com.example.studentapi.signupAndLogin.controller;

import com.example.studentapi.signupAndLogin.dto.request.UserRequest;
import com.example.studentapi.signupAndLogin.dto.response.UserResponse;
import com.example.studentapi.signupAndLogin.entity.User;
import com.example.studentapi.signupAndLogin.repository.UserRepository;
import com.example.studentapi.signupAndLogin.service.UserService;
import com.example.studentapi.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.regex.Pattern;

@Controller
public class WebController {

    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserRepository userRepository;

    private static final String BASE_API_URL = "http://localhost:9090/api/auth";

    @GetMapping("/auth/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userRequest", new UserRequest());
        return "register";
    }

    @PostMapping("/auth/register")
    public String registerUser(UserRequest userRequest, Model model) {
        try {
            if (!isValidEmail(userRequest.getEmail())) {
                model.addAttribute("error", "Invalid email address.");
                return "register";
            }
            UserResponse response = userService.registerUser(userRequest);
            model.addAttribute("message", "User registered successfully");
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/auth/login")
    public String showLoginForm(Model model) {
        model.addAttribute("userRequest", new UserRequest());
        return "login";
    }

    @PostMapping("/auth/login")
    public String loginUser(UserRequest userRequest, Model model) {
        try {
            String url = BASE_API_URL + "/login";
            ResponseEntity<ApiResponse> response = restTemplate.postForEntity(url, userRequest, ApiResponse.class);
            model.addAttribute("user", response.getBody().getData());
            model.addAttribute("message", response.getBody().getMessage());
            return "home";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/auth/home")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/auth/forgot-password")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("email", "");
        return "forgot-password";
    }

    @PostMapping("/auth/forgot-password")
    public String handleForgotPassword(@RequestParam String email, Model model) {
        try {
            if (!isValidEmail(email)) {
                model.addAttribute("error", "Invalid email address.");
                return "forgot-password";
            }

            if (!userService.isEmailRegistered(email)) {
                model.addAttribute("error", "No account found with that email address.");
                return "forgot-password";
            }

            String token = userService.createPasswordResetToken(email);
            System.out.println("Received token: " + token);
            String resetLink = "http://localhost:9090/auth/reset-password?token=" + token;


            sendPasswordResetEmail(email, resetLink);

            model.addAttribute("message", "Password reset email sent successfully.");
            return "responseForgetPassword";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to send password reset email: " + e.getMessage());
            return "forgot-password";
        }
    }



    private void sendPasswordResetEmail(String to, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, please click the following link: " + resetLink);
        mailSender.send(message);
    }

    @GetMapping("/auth/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        try {
            System.out.println("Token received in controller: " + token);
            model.addAttribute("token", token);
            return "reset-Password";
        } catch (Exception e) {
            model.addAttribute("error", "Invalid or expired token.");
            return "error";
        }
    }

    @PostMapping("/auth/reset-password")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestParam("newPassword") String newPassword, Model model) {
        try {
            System.out.println("Received token: " + token);
            userService.resetPasswordByToken(token, newPassword);
            model.addAttribute("message", "Password reset successfully.");
            return "response";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to reset password: " + e.getMessage());
            return "reset-Password";
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}





