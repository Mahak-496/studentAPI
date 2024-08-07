package com.example.studentapi.subject.service;

import com.example.studentapi.subject.dto.Request.SubjectRequestDto;
import com.example.studentapi.subject.dto.Response.SubjectResponseDto;

import java.util.List;

public interface ISubjectService {
    List<SubjectResponseDto> getAllSubjectDetails();

    SubjectResponseDto saveSubject(SubjectRequestDto subjectRequestDTO);

    void deleteSubject(int id);

    boolean checkIfSubjectExists(int id);

    SubjectResponseDto updateSubject(SubjectRequestDto subjectRequestDTO, int id);
}
