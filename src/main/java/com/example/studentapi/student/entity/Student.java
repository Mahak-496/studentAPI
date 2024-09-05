package com.example.studentapi.student.entity;

import com.example.studentapi.signupAndLogin.entity.User;
import com.example.studentapi.standard.entity.Standard;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "students")
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;


    @NotEmpty(message = "name is required")
    @Column(name = "student_name")
    private String name;

    @NotEmpty(message = "email is required")
    @Email(message = "Email should be valid")
    @Column(unique = true, name = "student_email")
    private String email;

    @Column(name = "student_address")
    private String address;

    @NotEmpty(message = "Phone Number is required")
    @Column(unique = true, name = "student_phone_number")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @Column(name = "student_school")
    private String school;

    @Column(name = "student_subjects")
    private String subjects;

    @OneToOne
    @JoinColumn(name = "standard_id")
    private Standard standard;


    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @CreationTimestamp
    private Timestamp createdOn;

    @UpdateTimestamp
    private Timestamp updateOn;

}
