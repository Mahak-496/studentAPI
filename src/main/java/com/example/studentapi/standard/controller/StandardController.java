package com.example.studentapi.standard.controller;

import com.example.studentapi.exceptions.StandardNotFoundException;
import com.example.studentapi.exceptions.StudentNotFoundException;
import com.example.studentapi.standard.dto.Request.StandardRequestDTO;
import com.example.studentapi.standard.dto.Response.StandardResponseDTO;
import com.example.studentapi.standard.service.StandardService;
import com.example.studentapi.subject.dto.Response.SubjectResponseDto;
import com.example.studentapi.teacher.dto.Response.TeacherResponseDTO;
import com.example.studentapi.utils.ApiResponse;
import com.example.studentapi.utils.ResponseSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StandardController {
    @Autowired
    private StandardService standardService;

    @GetMapping("/allStandard")
    public ResponseEntity<Object> getAllStandard() {
        try {
            List<StandardResponseDTO> list = standardService.getAllStandardDetails();
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Standard retrieved successfully")
                    .data(list)
                    .statusCode(HttpStatus.OK.value())
                    .build();
            return ResponseSender.send(apiResponse);
        } catch (RuntimeException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message(e.getMessage())
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .build();
            return ResponseSender.send(apiResponse);
        }
    }

    @PostMapping("/addStandards")
    public ResponseEntity<Object> createStandard(@RequestBody StandardRequestDTO standardRequestDTO) {
        try {
            StandardResponseDTO responseDTO = standardService.saveStandard(standardRequestDTO);
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Standard saved successfully")
                    .data(responseDTO)
                    .statusCode(HttpStatus.CREATED.value())
                    .build();
            return ResponseSender.send(apiResponse);
        } catch (Exception e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Something went wrong")
                    .devMessage(e.getMessage())
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
            return ResponseSender.send(apiResponse);
        }
    }

    @PutMapping("/UpdateStandard/{id}")
    public ResponseEntity<Object> updateStandard(@PathVariable("id") int id, @RequestBody StandardRequestDTO standardRequestDTO) {
        ApiResponse apiResponse;

        try {
            StandardResponseDTO updatedStandard = standardService.updateStandard(standardRequestDTO, id);
            apiResponse = ApiResponse.builder()
                    .message("Standard updated successfully using ID")
                    .statusCode(200)
                    .data(updatedStandard)
                    .build();
            return ResponseSender.send(apiResponse);
        } catch (StudentNotFoundException e) {
            apiResponse = ApiResponse.builder()
                    .message(e.getMessage())
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build();
            return ResponseSender.send(apiResponse);
        }
    }

    @DeleteMapping("/deleteStandard/{id}")
    public ResponseEntity<Object> deleteStandard(@PathVariable("id") int id) {
        ApiResponse apiResponse;

        try {
            boolean standardExists = standardService.checkIfStandardExists(id);
            if (standardExists) {
                standardService.deleteStandard(id);
                apiResponse = ApiResponse.builder()
                        .message("Standard deleted successfully")
                        .statusCode(HttpStatus.OK.value())
                        .build();
                return ResponseSender.send(apiResponse);
            } else {
                apiResponse = ApiResponse.builder()
                        .message("Standard with ID " + id + " does not exist")
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .build();
                return ResponseSender.send(apiResponse);
            }
        } catch (Exception e) {
            apiResponse = ApiResponse.builder()
                    .message("Something went wrong")
                    .devMessage(e.getMessage())
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
            return ResponseSender.send(apiResponse);
        }
    }

    @GetMapping("/teachers/{id}")
    public ResponseEntity<Object> getTeachersByStandardId(@PathVariable("id") int id) {
        try {
            List<TeacherResponseDTO> teachers = standardService.getTeachersByStandardId(id);
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Teachers retrieved successfully")
                    .data(teachers)
                    .statusCode(HttpStatus.OK.value())
                    .build();
            return ResponseSender.send(apiResponse);
        } catch (StandardNotFoundException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message(e.getMessage())
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .build();
            return ResponseSender.send(apiResponse);
        }
    }

    @GetMapping("/subjects/{id}")
    public ResponseEntity<Object> getSubjectsByStandardId(@PathVariable("id") int id) {
        try {
            List<SubjectResponseDto> subjects = standardService.getSubjectsByStandardId(id);
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Subjects retrieved successfully")
                    .data(subjects)
                    .statusCode(HttpStatus.OK.value())
                    .build();
            return ResponseSender.send(apiResponse);
        } catch (StandardNotFoundException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message(e.getMessage())
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .build();
            return ResponseSender.send(apiResponse);
        }
    }



}
