package com.example.studentapi.subject.service;

import com.example.studentapi.exceptions.StandardNotFoundException;
import com.example.studentapi.exceptions.SubjectNotFoundException;
import com.example.studentapi.standard.repository.StandardRepository;
import com.example.studentapi.subject.dto.Mapper.SubjectMapper;
import com.example.studentapi.subject.dto.Request.SubjectRequestDto;
import com.example.studentapi.subject.dto.Response.SubjectResponseDto;
import com.example.studentapi.subject.entity.Subject;
import com.example.studentapi.subject.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService implements ISubjectService {
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private StandardRepository standardRepository;

    @Override
    public List<SubjectResponseDto> getAllSubjectDetails() {
        List<Subject> subject = subjectRepository.findAll();
        if (subject.isEmpty()) {
            throw new SubjectNotFoundException("No Subject found");
        }
        return subject.stream()
                .map(SubjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SubjectResponseDto saveSubject(SubjectRequestDto subjectRequestDTO) {
        Subject subject = SubjectMapper.toEntity(subjectRequestDTO);
        if (subjectRequestDTO.getClassId() != null) {
            var standard = standardRepository.findById(subjectRequestDTO.getClassId());
            if (standard.isEmpty()) {
                throw new StandardNotFoundException("No Standard found with provided id!");
            }
            subject.setStandard(standard.get());
        }
        Subject savedSubject = subjectRepository.save(subject);
        return SubjectMapper.toResponseDTO(savedSubject);
    }

    @Override
    public void deleteSubject(int id) {
        if (!subjectRepository.existsById(id)) {
            throw new SubjectNotFoundException("No Subject found with id: " + id);

        }
        subjectRepository.deleteById(id);
    }

    @Override
    public boolean checkIfSubjectExists(int id) {
        return subjectRepository.existsById(id);
    }

    @Override
    public SubjectResponseDto updateSubject(SubjectRequestDto subjectRequestDTO, int id) {
        if (!subjectRepository.existsById(id)) {
            throw new SubjectNotFoundException("Subject not found with ID: " + id);
        }

        Subject subject = SubjectMapper.toEntity(subjectRequestDTO);
        if (subjectRequestDTO.getClassId() != null) {
            var standard = standardRepository.findById(subjectRequestDTO.getClassId());
            if (standard.isEmpty()) {
                throw new StandardNotFoundException("No Standard found with provided id!");
            }
            subject.setStandard(standard.get());
        }

        subject.setId(id);
        Subject savedSubject = subjectRepository.save(subject);
        return SubjectMapper.toResponseDTO(savedSubject);
    }

}
