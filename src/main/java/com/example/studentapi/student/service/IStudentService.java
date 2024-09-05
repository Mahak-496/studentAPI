package com.example.studentapi.student.service;

import com.example.studentapi.signupAndLogin.entity.User;
import com.example.studentapi.student.dto.groupStudents.GroupStudents;
import com.example.studentapi.student.dto.request.StudentRequestDTO;
import com.example.studentapi.student.dto.response.StudentResponseDTO;
import com.example.studentapi.student.entity.Student;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IStudentService {

    List<StudentResponseDTO> getAllStudents();

//    StudentResponseDTO saveStudent(StudentRequestDTO studentRequestDTO, User teacher);

//    StudentResponseDTO addStudent(String authHeader, StudentRequestDTO studentRequestDTO);
//
    @RolesAllowed("TEACHER")
    StudentResponseDTO addStudent(StudentRequestDTO studentRequestDTO, User teacher);

//    StudentResponseDTO addStudent(StudentRequestDTO studentRequestDTO, String teacherEmail);

//    StudentResponseDTO addStudent(StudentRequestDTO studentRequestDTO, User teacher);

    Optional<StudentResponseDTO> getStudentByEmail(String email);

    Optional<StudentResponseDTO> getStudentByPhoneNumber(String phoneNumber);

    Optional<StudentResponseDTO> getStudentsByID(int id);

    List<StudentResponseDTO> getStudentsBySubjects(String subjects);

    void deleteStudent(int id);

//    StudentResponseDTO updateUser(StudentRequestDTO studentRequestDTO, int id);

    boolean checkIfStudentExists(int id);

    List<StudentResponseDTO> getStudentByClassID(int id);

    Page<StudentResponseDTO> findStudentsCreatedBetween(String startDate, String endDate, String search, Integer standardId, int pageNo, int pageSize);

    List<GroupStudents>  groupStudentBasedOnClass();

    List<Student> listAll();

}
