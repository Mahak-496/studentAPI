package com.example.studentapi.teacher.service;

import com.example.studentapi.signupAndLogin.entity.User;
import com.example.studentapi.teacher.dto.Request.TeacherRequestDTO;
import com.example.studentapi.teacher.dto.Response.TeacherResponseDTO;

import java.util.List;

public interface ITeacherService {
    List<TeacherResponseDTO> getAllTeacherDetails();

//    TeacherResponseDTO saveTeacher(TeacherRequestDTO teacherRequestDTO);

    TeacherResponseDTO addTeacher(String authHeader, TeacherRequestDTO teacherRequestDTO);

    TeacherResponseDTO addTeacher(TeacherRequestDTO teacherRequestDTO, User headmaster);

    void deleteTeacher(int id);


    boolean checkIfTeacherExists(int id);

    TeacherResponseDTO updateTeacher(TeacherRequestDTO teacherRequestDTO, int id) throws Exception;
}
