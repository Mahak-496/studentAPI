package com.example.studentapi.teacher.service;

import com.example.studentapi.exceptions.TeacherNotFoundException;
import com.example.studentapi.signupAndLogin.configuration.JwtService;
import com.example.studentapi.signupAndLogin.entity.User;
import com.example.studentapi.signupAndLogin.repository.UserRepository;
import com.example.studentapi.signupAndLogin.service.UserService;
import com.example.studentapi.standard.entity.Standard;
import com.example.studentapi.standard.repository.StandardRepository;
import com.example.studentapi.teacher.dto.Mapper.TeacherMapper;
import com.example.studentapi.teacher.dto.Request.TeacherRequestDTO;
import com.example.studentapi.teacher.dto.Response.TeacherResponseDTO;
import com.example.studentapi.teacher.entity.Teacher;
import com.example.studentapi.teacher.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService implements ITeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StandardRepository standardRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<TeacherResponseDTO> getAllTeacherDetails() {
        List<Teacher> teacher = teacherRepository.findAll();
        if (teacher.isEmpty()) {
            throw new TeacherNotFoundException("No Teacher found");
        }
        return teacher.stream()
                .map(TeacherMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherResponseDTO addTeacher(String authHeader, TeacherRequestDTO teacherRequestDTO) {
        return null;
    }


//    @Override
//    public TeacherResponseDTO saveTeacher(TeacherRequestDTO teacherRequestDTO) {
//        Teacher teacher = TeacherMapper.toEntity(teacherRequestDTO);
//        Teacher savedTeacher = teacherRepository.save(teacher);
//        return TeacherMapper.toResponseDTO(savedTeacher);
//    }


    @Override
    public TeacherResponseDTO addTeacher(TeacherRequestDTO teacherRequestDTO, User headmaster) {
        Teacher teacher = TeacherMapper.tosaveTeacherEntity(teacherRequestDTO,headmaster);
        teacher.setHeadmaster(headmaster);
        Teacher savedTeacher = teacherRepository.save(teacher);
        return TeacherMapper.toResponseDTO(savedTeacher);
    }


    @Override
    public void deleteTeacher(int id) {
        if (!teacherRepository.existsById(id)) {
            throw new TeacherNotFoundException("No Teacher found with id: " + id);

        }
        teacherRepository.deleteById(id);
    }

    @Override
    public boolean checkIfTeacherExists(int id) {
        return teacherRepository.existsById(id);
    }

    @Override
    public TeacherResponseDTO updateTeacher(TeacherRequestDTO teacherRequestDTO, int id) throws Exception {
        if (!teacherRepository.existsById(id)) {
            throw new TeacherNotFoundException("Teacher not found with ID: " + id);
        }
        Teacher teacher = TeacherMapper.toEntity(teacherRequestDTO);
        if (teacherRequestDTO.getClassId() != null) {
            var standard = standardRepository.findById(teacherRequestDTO.getClassId());
            if (standard.isEmpty()) {
                throw new Exception("No Standard found with provided id!");
            }
            teacher.setStandard((List<Standard>) standard.get());
        }

        teacher.setId(id);
        Teacher savedTeacher = teacherRepository.save(teacher);
        return TeacherMapper.toResponseDTO(savedTeacher);
    }
}
