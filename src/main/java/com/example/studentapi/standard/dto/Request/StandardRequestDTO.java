package com.example.studentapi.standard.dto.Request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StandardRequestDTO {
    private String name;
    private String description;
    private List<Integer> teacherIds;
    private List<Integer> subjectIds;
}
