package com.example.studentapi.student.service;

import com.example.studentapi.exceptions.StudentNotFoundException;
import com.example.studentapi.standard.entity.Standard;
import com.example.studentapi.standard.repository.StandardRepository;
import com.example.studentapi.student.dto.mapper.StudentMapper;
import com.example.studentapi.student.dto.request.StudentRequestDTO;
import com.example.studentapi.student.dto.response.StudentResponseDTO;
import com.example.studentapi.student.entity.Student;
import com.example.studentapi.student.repository.StudentRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class StudentService implements IStudentService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("\\d{10}");
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StandardRepository standardRepository;

    @Override
    public List<StudentResponseDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        if (students.isEmpty()) {
            throw new StudentNotFoundException("No students found");
        }
        return students.stream()
                .map(StudentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponseDTO saveStudent(StudentRequestDTO studentRequestDTO) {
        validateStudentRequestDTO(studentRequestDTO);
        Standard standard = standardRepository.findById(studentRequestDTO.getStandardId())
                .orElseThrow(() -> new ValidationException("Standard not found with id: " + studentRequestDTO.getStandardId()));
        Student student = StudentMapper.toEntity(studentRequestDTO);
        student.setStandard(standard);
        Student savedStudent = studentRepository.save(student);
        return StudentMapper.toResponseDTO(savedStudent);
    }

    @Override
    public Optional<StudentResponseDTO> getStudentByEmail(String email) {
        return Optional.ofNullable(studentRepository.findByEmail(email)
                .map(StudentMapper::toResponseDTO)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with email: " + email)));
    }

    @Override
    public Optional<StudentResponseDTO> getStudentByPhoneNumber(String phoneNumber) {
        return Optional.ofNullable(studentRepository.findByPhoneNumber(phoneNumber)
                .map(StudentMapper::toResponseDTO)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with phone number: " + phoneNumber)));
    }

    @Override
    public Optional<StudentResponseDTO> getStudentsByID(int id) {
        return Optional.ofNullable(studentRepository.findById(id)
                .map(StudentMapper::toResponseDTO)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id)));

    }

    @Override
    public List<StudentResponseDTO> getStudentsBySubjects(String subjects) {
        List<Student> students = studentRepository.findBySubjects(subjects);
        if (students.isEmpty()) {
            throw new StudentNotFoundException("No students found with subjects: " + subjects);
        }
        return studentRepository.findBySubjects(subjects).stream()
                .map(StudentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    @Override
    public void deleteStudent(int id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("No student found with id: " + id);
        }
        studentRepository.deleteById(id);
    }

    @Override
    public StudentResponseDTO updateUser(StudentRequestDTO studentRequestDTO, int id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found with ID: " + id);
        }

        Standard standard = standardRepository.findById(studentRequestDTO.getStandardId())
                .orElseThrow(() -> new ValidationException("Standard not found with id: " + studentRequestDTO.getStandardId()));

        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + id));

        Student updatedStudent = StudentMapper.toEntity(studentRequestDTO);
        updatedStudent.setId(id);
        updatedStudent.setStandard(standard);
        Student savedStudent = studentRepository.save(updatedStudent);
        return StudentMapper.toResponseDTO(savedStudent);
    }

    @Override
    public boolean checkIfStudentExists(int id) {
        return studentRepository.existsById(id);
    }

    @Override
    public List<StudentResponseDTO> getStudentByClassID(int standardId) {
        List<Student> students = studentRepository.findByStandardId(standardId);
        if (students.isEmpty()) {
            throw new StudentNotFoundException("No students found with id: " + standardId);
        }
        return students.stream()
                .map(StudentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    private void validateStudentRequestDTO(StudentRequestDTO dto) {
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new ValidationException("Email cannot be empty");
        }
        if (!EMAIL_PATTERN.matcher(dto.getEmail()).matches()) {
            throw new ValidationException("Email format is incorrect");
        }
        if (dto.getPhoneNumber() == null || dto.getPhoneNumber().length() != 10) {
            throw new ValidationException("Phone number must be 10 digits");
        }
        if (!PHONE_PATTERN.matcher(dto.getPhoneNumber()).matches()) {
            throw new ValidationException("Phone number format is incorrect");
        }
    }
}
