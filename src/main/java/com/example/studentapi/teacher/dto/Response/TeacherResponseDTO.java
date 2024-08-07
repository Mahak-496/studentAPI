package com.example.studentapi.teacher.dto.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherResponseDTO {
    private int id;
    private String name;
    private String description;
    private String address;
//    private String standardName;
}
