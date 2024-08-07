package com.example.studentapi.standard.entity;

import com.example.studentapi.subject.entity.Subject;
import com.example.studentapi.teacher.entity.Teacher;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "standard")
@AllArgsConstructor
public class Standard {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @NotEmpty(message = "Class name is required")
    @Column(name = "class_name")
    private String name;

    @NotEmpty(message = "Description is required")
    @Column(name = "description")
    private String description;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "teacher_id")
//    private Teacher teacher;

//    @OneToMany(mappedBy = "standard", cascade = CascadeType.ALL)
//    private List<Teacher> teachers = new ArrayList<>();

    @ManyToMany
    private List<Teacher> teachers = new ArrayList<>();



    @OneToMany(mappedBy = "standard", cascade = CascadeType.ALL)
    private List<Subject> subjects = new ArrayList<>();


}
