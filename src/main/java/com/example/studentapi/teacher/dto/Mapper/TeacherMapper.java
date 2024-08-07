package com.example.studentapi.teacher.dto.Mapper;

import com.example.studentapi.teacher.dto.Request.TeacherRequestDTO;
import com.example.studentapi.teacher.dto.Response.TeacherResponseDTO;
import com.example.studentapi.teacher.entity.Teacher;

public class TeacherMapper {
    public static Teacher toEntity(TeacherRequestDTO dto) {
        return Teacher.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .address(dto.getAddress())
                .build();
    }

    public static TeacherResponseDTO toResponseDTO(Teacher entity) {
        return TeacherResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .address(entity.getAddress())
//                .standardName(entity.getStandard() != null ? entity.getStandard().getName() : null)
                .build();
    }
}
