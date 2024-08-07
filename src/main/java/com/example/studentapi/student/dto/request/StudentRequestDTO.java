package com.example.studentapi.student.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentRequestDTO {
    @NotEmpty(message = "name is required")
    private String name;

    private String email;

    private String address;

    private String phoneNumber;
    @NotEmpty(message = "School is required")
    private String school;
    @NotEmpty(message = "Subjects are required")
    private String subjects;
    private int standardId;


}
