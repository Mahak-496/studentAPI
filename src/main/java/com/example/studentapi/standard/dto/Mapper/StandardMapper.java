package com.example.studentapi.standard.dto.Mapper;

import com.example.studentapi.signupAndLogin.entity.User;
import com.example.studentapi.standard.dto.Request.StandardRequestDTO;
import com.example.studentapi.standard.dto.Response.StandardResponseDTO;
import com.example.studentapi.standard.entity.Standard;
import com.example.studentapi.student.dto.request.StudentRequestDTO;
import com.example.studentapi.student.entity.Student;
import com.example.studentapi.subject.dto.Mapper.SubjectMapper;
import com.example.studentapi.teacher.dto.Mapper.TeacherMapper;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class StandardMapper {
    public static Standard toEntity(StandardRequestDTO dto) {
        return Standard.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }



    public static Standard tosaveStandardEntity(StandardRequestDTO dto, User headmaster) {
        return Standard.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .headmaster(headmaster)
                .build();
    }

    public static StandardResponseDTO toResponseDTO(Standard entity) {
        return StandardResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .teachers(entity.getTeachers() != null ? entity.getTeachers().stream()
                        .map(TeacherMapper::toResponseDTO)
                        .collect(Collectors.toList()) : new ArrayList<>())
                .subjects(entity.getSubjects() != null ? entity.getSubjects().stream()
                        .map(SubjectMapper::toResponseDTO)
                        .collect(Collectors.toList()) : new ArrayList<>())
                .build();
//          .teacherName(entity.getTeacher() != null ? entity.getTeacher().getName() : null)
    }


}
