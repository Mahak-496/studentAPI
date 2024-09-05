package com.example.studentapi.signupAndLogin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "password_reset_token")
public class PasswordResetToken {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String token;
        private LocalDateTime expiryDate;

        @ManyToOne
        private User user;

        public PasswordResetToken(String token, User user) {
                this.token = token;
                this.user = user;
                this.expiryDate = LocalDateTime.now().plusHours(1); // Token valid for 1 hour
        }


}

