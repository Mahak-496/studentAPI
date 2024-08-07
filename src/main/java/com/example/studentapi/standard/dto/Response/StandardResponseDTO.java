package com.example.studentapi.standard.dto.Response;

import com.example.studentapi.subject.dto.Response.SubjectResponseDto;
import com.example.studentapi.teacher.dto.Response.TeacherResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StandardResponseDTO {
    private int id;
    private String name;
    private String description;
    private List<TeacherResponseDTO> teachers;
    private List<SubjectResponseDto> subjects;


}
