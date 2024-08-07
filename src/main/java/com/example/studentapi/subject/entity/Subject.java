package com.example.studentapi.subject.entity;

import com.example.studentapi.standard.entity.Standard;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "subject")
@AllArgsConstructor
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @NotEmpty(message = "Class name is required")
    @Column(name = "subject_name")
    private String name;

    @NotEmpty(message = "Description is required")
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_id")
    private Standard standard;


}
