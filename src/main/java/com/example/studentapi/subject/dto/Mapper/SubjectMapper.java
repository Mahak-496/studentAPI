package com.example.studentapi.subject.dto.Mapper;

import com.example.studentapi.subject.dto.Request.SubjectRequestDto;
import com.example.studentapi.subject.dto.Response.SubjectResponseDto;
import com.example.studentapi.subject.entity.Subject;

public class SubjectMapper {
    public static Subject toEntity(SubjectRequestDto dto) {
        return Subject.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public static SubjectResponseDto toResponseDTO(Subject entity) {
        return SubjectResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}
