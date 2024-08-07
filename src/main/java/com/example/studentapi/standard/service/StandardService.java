
package com.example.studentapi.standard.service;

import com.example.studentapi.exceptions.StandardNotFoundException;
import com.example.studentapi.standard.dto.Mapper.StandardMapper;
import com.example.studentapi.standard.dto.Request.StandardRequestDTO;
import com.example.studentapi.standard.dto.Response.StandardResponseDTO;
import com.example.studentapi.standard.entity.Standard;
import com.example.studentapi.standard.repository.StandardRepository;
import com.example.studentapi.subject.dto.Mapper.SubjectMapper;
import com.example.studentapi.subject.dto.Response.SubjectResponseDto;
import com.example.studentapi.subject.entity.Subject;
import com.example.studentapi.subject.repository.SubjectRepository;
import com.example.studentapi.teacher.dto.Mapper.TeacherMapper;
import com.example.studentapi.teacher.dto.Response.TeacherResponseDTO;
import com.example.studentapi.teacher.entity.Teacher;
import com.example.studentapi.teacher.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StandardService implements IStandardService {

    @Autowired
    private StandardRepository standardRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public StandardResponseDTO saveStandard(StandardRequestDTO standardRequestDTO) {
        Standard standard = StandardMapper.toEntity(standardRequestDTO);

        if (standardRequestDTO.getSubjectIds() != null && !standardRequestDTO.getSubjectIds().isEmpty()) {
            List<Subject> subjects = subjectRepository.findAllById(standardRequestDTO.getSubjectIds());
            standard.setSubjects(subjects);
        }

        if (standardRequestDTO.getTeacherIds() != null && !standardRequestDTO.getTeacherIds().isEmpty()) {
            List<Teacher> teachers = teacherRepository.findAllById(standardRequestDTO.getTeacherIds());
            standard.setTeachers(teachers);
        }

        Standard savedStandard = standardRepository.save(standard);
        return StandardMapper.toResponseDTO(savedStandard);
    }

    @Override
    public void deleteStandard(int id) {
        if (!standardRepository.existsById(id)) {
            throw new StandardNotFoundException("No Standard found with id: " + id);
        }
        standardRepository.deleteById(id);
    }

    @Override
    public boolean checkIfStandardExists(int id) {
        return standardRepository.existsById(id);
    }

    @Override
    public StandardResponseDTO updateStandard(StandardRequestDTO standardRequestDTO, int id) {
        Standard existingStandard = standardRepository.findById(id)
                .orElseThrow(() -> new StandardNotFoundException("Standard not found with ID: " + id));

        existingStandard.setName(standardRequestDTO.getName());
        existingStandard.setDescription(standardRequestDTO.getDescription());


        if (standardRequestDTO.getSubjectIds() != null && !standardRequestDTO.getSubjectIds().isEmpty()) {
            List<Subject> subjects = subjectRepository.findAllById(standardRequestDTO.getSubjectIds());
            existingStandard.setSubjects(subjects);
            for (Subject subject : subjects) {
                subject.setStandard(existingStandard);
                subjectRepository.save(subject);
            }
        } else {
            existingStandard.setSubjects(new ArrayList<>());
        }
        if (standardRequestDTO.getTeacherIds() != null) {
            List<Teacher> teachers = teacherRepository.findAllById(standardRequestDTO.getTeacherIds());
            existingStandard.setTeachers(teachers);
            for (Teacher teacher : teachers) {

                if (teacher.getStandard() == null) {
                    teacher.setStandard(new ArrayList<>());
                }
                if (!teacher.getStandard().contains(existingStandard)) {
                    teacher.getStandard().add(existingStandard);
                    teacherRepository.save(teacher);
                }
            }
        }

        Standard updatedStandard = standardRepository.save(existingStandard);
        return StandardMapper.toResponseDTO(updatedStandard);
    }

    @Override
    public List<StandardResponseDTO> getAllStandardDetails() {
        List<Standard> standards = standardRepository.findAll();
        if (standards.isEmpty()) {
            throw new StandardNotFoundException("No Standard found");
        }
        return standards.stream()
                .map(StandardMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherResponseDTO> getTeachersByStandardId(int standardId) {
        Standard standard = standardRepository.findById(standardId)
                .orElseThrow(() -> new StandardNotFoundException("Standard not found with ID: " + standardId));

        return standard.getTeachers().stream()
                .map(TeacherMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubjectResponseDto> getSubjectsByStandardId(int standardId) {
        Standard standard = standardRepository.findById(standardId)
                .orElseThrow(() -> new StandardNotFoundException("Standard not found with ID: " + standardId));
        return standard.getSubjects().stream()
                .map(SubjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
