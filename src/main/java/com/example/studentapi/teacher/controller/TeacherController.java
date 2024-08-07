package com.example.studentapi.teacher.controller;

import com.example.studentapi.teacher.dto.Request.TeacherRequestDTO;
import com.example.studentapi.teacher.dto.Response.TeacherResponseDTO;
import com.example.studentapi.teacher.service.TeacherService;
import com.example.studentapi.utils.ApiResponse;
import com.example.studentapi.utils.ResponseSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/allTeacher")
    public ResponseEntity<Object> getAllTeacher() {
        try {
            List<TeacherResponseDTO> list = teacherService.getAllTeacherDetails();
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Teacher retrieved successfully")
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


    @PostMapping("/addTeachers")
    public ResponseEntity<Object> createTeacher(@RequestBody TeacherRequestDTO teacherRequestDTO) {
        try {
            TeacherResponseDTO responseDTO = teacherService.saveTeacher(teacherRequestDTO);
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Teacher saved successfully")
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


    @PutMapping("/UpdateTeacher/{id}")
    public ResponseEntity<Object> updateTeacher(@PathVariable("id") int id, @RequestBody TeacherRequestDTO teacherRequestDTO) {
        ApiResponse apiResponse;

        try {
            TeacherResponseDTO updatedTeacher = teacherService.updateTeacher(teacherRequestDTO, id);
            apiResponse = ApiResponse.builder()
                    .message("Teacher updated successfully using ID")
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


    @DeleteMapping("/deleteTeacher/{id}")
    public ResponseEntity<Object> deleteTeacher(@PathVariable("id") int id) {
        ApiResponse apiResponse;

        try {

            boolean teacherExists = teacherService.checkIfTeacherExists(id);
            if (teacherExists) {
                teacherService.deleteTeacher(id);
                apiResponse = ApiResponse.builder()
                        .message("Teacher deleted successfully")
                        .statusCode(HttpStatus.OK.value())
                        .build();
                return ResponseSender.send(apiResponse);
            } else {
                apiResponse = ApiResponse.builder()
                        .message("Teacher with ID " + id + " does not exist")
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

