package com.example.studentapi.subject.dto.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubjectRequestDto {
    private String name;
    private String description;
    private Integer classId;
}
