package com.example.studentapi.student.controller;

import com.example.studentapi.exceptions.StudentNotFoundException;
import com.example.studentapi.exceptions.ValidationException;
import com.example.studentapi.student.dto.groupStudents.GroupStudents;
import com.example.studentapi.student.dto.request.StudentRequestDTO;
import com.example.studentapi.student.dto.response.StudentResponseDTO;
import com.example.studentapi.student.entity.Student;
import com.example.studentapi.student.service.StudentService;
import com.example.studentapi.utils.ApiResponse;
import com.example.studentapi.utils.ResponseSender;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/allStudents")
    public ResponseEntity<Object> getAllStudents() {
        try {
            List<StudentResponseDTO> list = studentService.getAllStudents();
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Students retrieved successfully")
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

    @PostMapping("/addStudents")
    public ResponseEntity<Object> createStudent(@Valid @RequestBody StudentRequestDTO studentRequestDTO) {
        try {
            StudentResponseDTO responseDTO = studentService.saveStudent(studentRequestDTO);
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Student saved successfully")
                    .data(responseDTO)
                    .statusCode(HttpStatus.CREATED.value())
                    .build();
            return ResponseSender.send(apiResponse);
        } catch (ValidationException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message(e.getMessage())
                    .statusCode(HttpStatus.BAD_REQUEST.value())
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

    @GetMapping("/getstudents/email/{email}")
    public ResponseEntity<Object> getStudentByEmail(@PathVariable String email) {
        ApiResponse apiResponse;
        try {
            Optional<StudentResponseDTO> student = studentService.getStudentByEmail(email);
            apiResponse = ApiResponse.builder()
                    .message("Students retrieved successfully using Email")
                    .data(student.get())
                    .statusCode(200)
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

    @GetMapping("/getstudents/phone/{phoneNumber}")
    public ResponseEntity<Object> getStudentByPhoneNumber(@PathVariable String phoneNumber) {
        ApiResponse apiResponse;
        try {
            Optional<StudentResponseDTO> student = studentService.getStudentByPhoneNumber(phoneNumber);
            apiResponse = ApiResponse.builder()
                    .message("Students retrieved successfully using Phone Number")
                    .data(student.get())
                    .statusCode(200)
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


    @GetMapping("/getstudents/id/{id}")
    public ResponseEntity<Object> getStudentByID(@PathVariable int id) {
        ApiResponse apiResponse;
        try {
            Optional<StudentResponseDTO> student = studentService.getStudentsByID(id);
            apiResponse = ApiResponse.builder()
                    .message("Students retrieved successfully using ID")
                    .data(student.get())
                    .statusCode(200)
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


    @GetMapping("/getstudents/subjects/{subjects}")
    public ResponseEntity<Object> getStudentBySubjects(@PathVariable String subjects) {
        ApiResponse apiResponse;

        try {
            List<StudentResponseDTO> list = studentService.getStudentsBySubjects(subjects);
            apiResponse = ApiResponse.builder()
                    .message("Students retrieved successfully using subjects")
                    .data(list)
                    .statusCode(200)
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

    @GetMapping("/getStudents/classid/{id}")
    public ResponseEntity<Object> getStudentById(@PathVariable int id) {
        ApiResponse apiResponse;

        try {
            List<StudentResponseDTO> list = studentService.getStudentByClassID(id);
            apiResponse = ApiResponse.builder()
                    .message("Students retrieved successfully using class id")
                    .data(list)
                    .statusCode(200)
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

//    @GetMapping("/between")
//    public List<StudentResponseDTO> getStudentsCreatedBetween(
//           @Nullable @RequestParam("startDate") String startDate,
//           @Nullable @RequestParam("endDate") String endDate) throws ParseException {
//        Timestamp fromDate = DateUtils.fromDateToTimestamp(startDate);
//        Timestamp toDate = DateUtils.toDateToTimestamp(endDate);
//        return studentService.findStudentsCreatedBetween(fromDate, toDate);
//    }

//    @GetMapping("/filterStudents")
//    public ResponseEntity<Object> getStudentsCreatedBetween(
//            @RequestParam(value = "startDate", required = false) String startDate,
//            @RequestParam(value = "endDate", required = false) String endDate,
//            @RequestParam(value = "search", required = false) String search,
//            @RequestParam(value = "standardId", required = false) Integer standardId
//    ) {
//        ApiResponse apiResponse;
//
//        try {
//            List<StudentResponseDTO> students = studentService.findStudentsCreatedBetween(startDate, endDate,search,standardId);
//            apiResponse = ApiResponse.builder()
//                    .message("Students retrieved successfully")
//                    .data(students)
//                    .statusCode(HttpStatus.OK.value())
//                    .build();
//            return ResponseSender.send(apiResponse);
//
//        } catch (IllegalArgumentException e) {
//            apiResponse = ApiResponse.builder()
//                    .message("Invalid date format: " + e.getMessage())
//                    .statusCode(HttpStatus.BAD_REQUEST.value())
//                    .build();
//
//            return ResponseSender.send(apiResponse);
//        } catch (Exception e) {
//            apiResponse = ApiResponse.builder()
//                    .message("An unexpected error occurred: " + e.getMessage())
//                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                    .build();
//            return ResponseSender.send(apiResponse);
//        }
//    }

    @GetMapping("/GroupStudent")
    public ResponseEntity<Object>groupStudentBasedOnClass(){
        try {
            List<GroupStudents> list = studentService.groupStudentBasedOnClass();
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Students retrieved successfully")
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

//    @GetMapping("/page")
//    public Page<StudentResponseDTO> getStudents(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        return studentService.getStudents(page, size);
//    }
    @GetMapping("/page")
    public ResponseEntity<Page<StudentResponseDTO>> getProducts(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "") int pageSize) {

        Page<StudentResponseDTO> students = studentService.getStudents(pageNo, pageSize);
        return ResponseEntity.ok(students);
    }



    @GetMapping("/filterStudents")
    public ResponseEntity<Object> getStudentsCreatedBetween(
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "standardId", required = false) Integer standardId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize)
    {
        ApiResponse apiResponse;

        try {
            Page<StudentResponseDTO> students = studentService.findStudentsCreatedBetween(startDate, endDate,search,standardId,pageNo,pageSize);
            apiResponse = ApiResponse.builder()
                    .message("Students retrieved successfully")
                    .data(students)
                    .statusCode(HttpStatus.OK.value())
                    .build();
            return ResponseSender.send(apiResponse);

        } catch (IllegalArgumentException e) {
            apiResponse = ApiResponse.builder()
                    .message("Invalid date format: " + e.getMessage())
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build();

            return ResponseSender.send(apiResponse);
        } catch (Exception e) {
            apiResponse = ApiResponse.builder()
                    .message("An unexpected error occurred: " + e.getMessage())
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();

            return ResponseSender.send(apiResponse);
        }
    }


    @PutMapping("/UpdateStudent/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") int id, @RequestBody StudentRequestDTO studentRequestDTO) {
        ApiResponse apiResponse;

        try {
            StudentResponseDTO updatedUser = studentService.updateUser(studentRequestDTO, id);
            apiResponse = ApiResponse.builder()
                    .message("Students updated successfully using ID")
                    .statusCode(200)
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


    @DeleteMapping("/deleteStudent/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable("id") int id) {
        ApiResponse apiResponse;

        try {

            boolean studentExists = studentService.checkIfStudentExists(id);
            if (studentExists) {
                studentService.deleteStudent(id);
                apiResponse = ApiResponse.builder()
                        .message("Student deleted successfully")
                        .statusCode(HttpStatus.OK.value())
                        .build();
                return ResponseSender.send(apiResponse);
            } else {
                apiResponse = ApiResponse.builder()
                        .message("Student with ID " + id + " does not exist")
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


//    @DeleteMapping("/deleteStudent/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable("id") int id) {
//        try {
//            studentService.deleteUser(id);
//            return ResponseEntity.noContent().build();
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }

//    @PutMapping("/UpdateStudent/{id}")
//    public ResponseEntity<StudentResponseDTO> updateUser(@PathVariable("id") int id, @RequestBody StudentRequestDTO studentRequestDTO) {
//        try {
//            StudentResponseDTO updatedUser = studentService.updateUser(studentRequestDTO, id);
//            return ResponseEntity.ok(updatedUser);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }


}


//  @GetMapping("/allStudents")
//    public ResponseEntity<List<StudentResponseDTO>> getAllStudents(){
//        List<StudentResponseDTO>list= this.studentService.getAllStudents();
//        if(list.size()<=0){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//        return ResponseEntity.ok(lis.orElseGet(() -> ResponseEntity.notFound().build());t);
//
//    }

//    @PostMapping("/addStudents")
//    public ResponseEntity<StudentResponseDTO> createStudent(@RequestBody StudentRequestDTO studentRequestDTO) {
//        StudentResponseDTO responseDTO = studentService.saveStudent(studentRequestDTO);
//        return ResponseEntity.ok(responseDTO);
//    }

//    @GetMapping("/getstudents/email/{email}")
//    public ResponseEntity<StudentResponseDTO> getStudentByEmail(@PathVariable String email) {
//        Optional<StudentResponseDTO> student = studentService.getStudentByEmail(email);
//        return student.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }

//    @GetMapping("/getstudents/phone/{phoneNumber}")
//    public ResponseEntity<StudentResponseDTO> getStudentByPhoneNumber(@PathVariable String phoneNumber) {
//        Optional<StudentResponseDTO> student = studentService.getStudentByPhoneNumber(phoneNumber);
//        return student.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }

//    @GetMapping("/getstudents/id/{id}")
//    public ResponseEntity<StudentResponseDTO> getStudentByID(@PathVariable int id){
//        Optional<StudentResponseDTO>student=studentService.getStudentsByID(id);
//        return student.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }

//
//    @GetMapping("/getstudents/subjects/{subjects}")
//    public ResponseEntity<List<StudentResponseDTO>> getStudentBySubjects(@PathVariable String subjects){
//        List<StudentResponseDTO>list=studentService.getStudentsBySubjects(subjects);
//        return ResponseEntity.ok(list);
//    }




