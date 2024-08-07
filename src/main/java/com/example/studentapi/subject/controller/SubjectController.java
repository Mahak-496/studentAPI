package com.example.studentapi.subject.controller;

import com.example.studentapi.subject.dto.Request.SubjectRequestDto;
import com.example.studentapi.subject.dto.Response.SubjectResponseDto;
import com.example.studentapi.subject.service.SubjectService;
import com.example.studentapi.utils.ApiResponse;
import com.example.studentapi.utils.ResponseSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping("/allSubjects")
    public ResponseEntity<Object> getAllSubjects() {
        try {
            List<SubjectResponseDto> list = subjectService.getAllSubjectDetails();
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Subject retrieved successfully")
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

    @PostMapping("/addSubjects")
    public ResponseEntity<Object> createSubjects(@RequestBody SubjectRequestDto subjectRequestDto) {
        try {
            SubjectResponseDto responseDTO = subjectService.saveSubject(subjectRequestDto);
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Subject saved successfully")
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

    @PutMapping("/UpdateSubject/{id}")
    public ResponseEntity<Object> updateSubject(@PathVariable("id") int id, @RequestBody SubjectRequestDto subjectRequestDto) {
        ApiResponse apiResponse;

        try {
            SubjectResponseDto updatedSubject = subjectService.updateSubject(subjectRequestDto, id);
            apiResponse = ApiResponse.builder()
                    .message("Suject updated successfully using ID")
                    .statusCode(200)
                    .build();
            return ResponseSender.send(apiResponse);
        } catch (Exception e) {
            apiResponse = ApiResponse.builder()
                    .message(e.getMessage())
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build();
            return ResponseSender.send(apiResponse);
        }
    }


    @DeleteMapping("/deleteSubjects/{id}")
    public ResponseEntity<Object> deleteTeacher(@PathVariable("id") int id) {
        ApiResponse apiResponse;

        try {

            boolean subjectExists = subjectService.checkIfSubjectExists(id);
            if (subjectExists) {
                subjectService.deleteSubject(id);
                apiResponse = ApiResponse.builder()
                        .message("Subjects deleted successfully")
                        .statusCode(HttpStatus.OK.value())
                        .build();
                return ResponseSender.send(apiResponse);
            } else {
                apiResponse = ApiResponse.builder()
                        .message("Subjests with ID " + id + " does not exist")
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


    @DeleteMapping("/deleteSubject/{id}")
    public ResponseEntity<Object> deleteSubject(@PathVariable("id") int id) {
        ApiResponse apiResponse;

        try {

            boolean subjectExists = subjectService.checkIfSubjectExists(id);
            if (subjectExists) {
                subjectService.deleteSubject(id);
                apiResponse = ApiResponse.builder()
                        .message("Subject deleted successfully")
                        .statusCode(HttpStatus.OK.value())
                        .build();
                return ResponseSender.send(apiResponse);
            } else {
                apiResponse = ApiResponse.builder()
                        .message("Subject with ID " + id + " does not exist")
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


}
