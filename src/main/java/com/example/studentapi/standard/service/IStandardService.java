package com.example.studentapi.standard.service;

import com.example.studentapi.standard.dto.Request.StandardRequestDTO;
import com.example.studentapi.standard.dto.Response.StandardResponseDTO;
import com.example.studentapi.subject.dto.Response.SubjectResponseDto;
import com.example.studentapi.teacher.dto.Response.TeacherResponseDTO;

import java.util.List;

public interface IStandardService {


    List<StandardResponseDTO> getAllStandardDetails();

    StandardResponseDTO saveStandard(StandardRequestDTO standardRequestDTO);


    void deleteStandard(int id);

    boolean checkIfStandardExists(int id);

    StandardResponseDTO updateStandard(StandardRequestDTO standardRequestDTO, int id);

    List<TeacherResponseDTO> getTeachersByStandardId(int standardId);

    List<SubjectResponseDto> getSubjectsByStandardId(int standardId);
}
