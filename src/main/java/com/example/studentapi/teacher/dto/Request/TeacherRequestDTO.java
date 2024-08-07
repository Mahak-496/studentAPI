package com.example.studentapi.teacher.dto.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherRequestDTO {
    private String name;
    private String description;
    private String address;
    private Integer classId;
}
