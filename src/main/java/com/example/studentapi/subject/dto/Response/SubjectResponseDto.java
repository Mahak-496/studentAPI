package com.example.studentapi.subject.dto.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubjectResponseDto {
    private int id;
    private String name;
    private String description;
}
