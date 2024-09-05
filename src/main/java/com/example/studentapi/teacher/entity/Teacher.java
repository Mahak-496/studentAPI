package com.example.studentapi.teacher.entity;

import com.example.studentapi.signupAndLogin.entity.User;
import com.example.studentapi.standard.entity.Standard;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "teacher")
@AllArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @NotEmpty(message = "Teacher name is required")
    @Column(name = "teacher_name")
    private String name;

    @NotEmpty(message = "description is required")
    @Column(name = "teacher_description")
    private String description;

    @Column(name = "teacher_address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "headmaster_id")
    private User headmaster;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "class_id")
//    private Standard standard;

    @ManyToMany
    @JoinTable(
            name = "teacher_standard",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "standard_id")
    )
    private List<Standard> standard = new ArrayList<>();


}
