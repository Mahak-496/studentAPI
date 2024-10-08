package com.example.studentapi.student.dto.mapper;

import com.example.studentapi.signupAndLogin.entity.User;
import com.example.studentapi.standard.dto.Mapper.StandardMapper;
import com.example.studentapi.student.dto.request.StudentRequestDTO;
import com.example.studentapi.student.dto.response.StudentResponseDTO;
import com.example.studentapi.student.entity.Student;


public class StudentMapper {
    public static Student toEntity(StudentRequestDTO dto) {
        return Student.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .address(dto.getAddress())
                .phoneNumber(dto.getPhoneNumber())
                .school(dto.getSchool())
                .subjects(dto.getSubjects())
//                .teacher(teacher)
                .build();
    }



        public static Student tosaveStudentEntity(StudentRequestDTO dto, User teacher) {
            return Student.builder()
                    .name(dto.getName())
                    .email(dto.getEmail())
                    .address(dto.getAddress())
                    .phoneNumber(dto.getPhoneNumber())
                    .school(dto.getSchool())
                    .subjects(dto.getSubjects())
                     .teacher(teacher)
                    .build();
        }

    public static StudentResponseDTO toResponseDTO(Student entity) {
        return StudentResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .phoneNumber(entity.getPhoneNumber())
                .school(entity.getSchool())
                .subjects(entity.getSubjects())
                .standard(entity.getStandard() != null ? StandardMapper.toResponseDTO(entity.getStandard()) : null)
//                .teacherId(entity.getTeacher() != null ? entity.getTeacher().getId() : null)
//                .standardDescription(entity.getStandard() != null ? entity.getStandard().getDescription() : null)
////                .TeacherName(entity.getStandard()!=null?entity.getStandard().getTeacher().getName():null)
                .build();
    }
}
