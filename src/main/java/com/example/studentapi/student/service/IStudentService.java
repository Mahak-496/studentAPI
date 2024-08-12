package com.example.studentapi.student.service;

import com.example.studentapi.student.dto.groupStudents.GroupStudents;
import com.example.studentapi.student.dto.request.StudentRequestDTO;
import com.example.studentapi.student.dto.response.StudentResponseDTO;
import com.example.studentapi.student.entity.Student;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
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

//    List<StudentResponseDTO> findStudentsCreatedBetween(Timestamp startDate, Timestamp endDate);

    //List<StudentResponseDTO> findStudentsCreatedBetween(String startDate, String endDate);

//    List<StudentResponseDTO> findStudentsCreatedBetween(String startDate, String endDate, String search);

//    List<StudentResponseDTO> findStudentsCreatedBetween(String startDate, String endDate, String search, Integer standardId);

    Page<StudentResponseDTO> findStudentsCreatedBetween(String startDate, String endDate, String search, Integer standardId, int pageNo, int pageSize);

    List<GroupStudents>  groupStudentBasedOnClass();

//    Page<Student> getStudents(int page, int size);
//    Map<Integer,List<StudentResponseDTO>>/ groupStudentBasedOnClass();
}
