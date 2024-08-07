package com.example.studentapi.student.service;

import com.example.studentapi.student.dto.request.StudentRequestDTO;
import com.example.studentapi.student.dto.response.StudentResponseDTO;

import java.util.List;
import java.util.Optional;

public interface IStudentService {

    List<StudentResponseDTO> getAllStudents();

    StudentResponseDTO saveStudent(StudentRequestDTO studentRequestDTO);

    Optional<StudentResponseDTO> getStudentByEmail(String email);

    Optional<StudentResponseDTO> getStudentByPhoneNumber(String phoneNumber);

    Optional<StudentResponseDTO> getStudentsByID(int id);

    List<StudentResponseDTO> getStudentsBySubjects(String subjects);

    void deleteStudent(int id);

    StudentResponseDTO updateUser(StudentRequestDTO studentRequestDTO, int id);

    boolean checkIfStudentExists(int id);

    List<StudentResponseDTO> getStudentByClassID(int id);

}
