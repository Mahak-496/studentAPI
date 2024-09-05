package com.example.studentapi.student.controller;

import com.example.studentapi.exceptions.StudentNotFoundException;
import com.example.studentapi.exceptions.ValidationException;
import com.example.studentapi.signupAndLogin.configuration.JwtService;
import com.example.studentapi.signupAndLogin.entity.User;
import com.example.studentapi.signupAndLogin.repository.UserRepository;
import com.example.studentapi.signupAndLogin.service.UserService;
import com.example.studentapi.student.dto.groupStudents.GroupStudents;
import com.example.studentapi.student.dto.request.StudentRequestDTO;
import com.example.studentapi.student.dto.response.StudentResponseDTO;
import com.example.studentapi.student.service.StudentService;
import com.example.studentapi.utils.ApiResponse;
import com.example.studentapi.utils.CsvFileGenerator;
import com.example.studentapi.utils.ResponseSender;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController

public class StudentController {

    private final String uploadDir = "uploads_temp/";
    @Autowired
    private StudentService studentService;
    @Autowired
    private CsvFileGenerator csvFileGenerator;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

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


//    @RolesAllowed("TEACHER")
//    @PostMapping("/addStudents")
//    public ResponseEntity<Object> createStudent(@RequestHeader("Authorization") String authHeader,
//                                                @Valid @RequestBody StudentRequestDTO studentRequestDTO) {
//        try {
//            StudentResponseDTO responseDTO = studentService.addStudent(authHeader, studentRequestDTO);
//            ApiResponse apiResponse = ApiResponse.builder()
//                    .message("Student saved successfully")
//                    .data(responseDTO)
//                    .statusCode(HttpStatus.CREATED.value())
//                    .build();
//            return ResponseSender.send(apiResponse);
//        } catch (ValidationException e) {
//            ApiResponse apiResponse = ApiResponse.builder()
//                    .message(e.getMessage())
//                    .statusCode(HttpStatus.FORBIDDEN.value())
//                    .build();
//            return ResponseSender.send(apiResponse);
//        } catch (UnauthorizedActionException e) {
//            ApiResponse apiResponse = ApiResponse.builder()
//                    .message(e.getMessage())
//                    .statusCode(HttpStatus.FORBIDDEN.value())
//                    .build();
//            return ResponseSender.send(apiResponse);
//        } catch (Exception e) {
//            ApiResponse apiResponse = ApiResponse.builder()
//                    .message("Something went wrong")
//                    .devMessage(e.getMessage())
//                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                    .build();
//            return ResponseSender.send(apiResponse);
//        }
//    }

    @RolesAllowed("ROLE_TEACHER")
    @PostMapping("/add")
    public ResponseEntity<Object> addStudent(@RequestBody StudentRequestDTO studentRequestDTO) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User teacher = userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User with email " + userDetails.getUsername() + " not found."));

            StudentResponseDTO addedStudent = studentService.addStudent(studentRequestDTO, teacher);

            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Student added successfully")
                    .data(addedStudent)
                    .statusCode(HttpStatus.CREATED.value())
                    .build();
            return ResponseSender.send(apiResponse);

        }  catch (ValidationException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message(e.getMessage())
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build();
            return ResponseSender.send(apiResponse);
        } catch (Exception e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("An error occurred: User with this email already exist " )
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

    @GetMapping("/export-to-csv")
    public void exportIntoCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"student.csv\"");
        csvFileGenerator.writeStudentsToCsv(studentService.getAllStudents(), response.getWriter());
    }

    @GetMapping("/GroupStudent")
    public ResponseEntity<Object> groupStudentBasedOnClass() {
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
            @RequestParam(defaultValue = "10") int pageSize) {
        ApiResponse apiResponse;

        try {
            Page<StudentResponseDTO> students = studentService.findStudentsCreatedBetween(startDate, endDate, search, standardId, pageNo, pageSize);
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

//    @PutMapping("/UpdateStudent/{id}")
//    public ResponseEntity<Object> updateUser(@PathVariable("id") int id, @RequestBody StudentRequestDTO studentRequestDTO) {
//        ApiResponse apiResponse;
//
//        try {
//            StudentResponseDTO updatedUser = studentService.updateUser(studentRequestDTO, id);
//            apiResponse = ApiResponse.builder()
//                    .message("Students updated successfully using ID")
//                    .statusCode(200)
//                    .build();
//            return ResponseSender.send(apiResponse);
//        } catch (StudentNotFoundException e) {
//            apiResponse = ApiResponse.builder()
//                    .message(e.getMessage())
//                    .statusCode(HttpStatus.BAD_REQUEST.value())
//                    .build();
//            return ResponseSender.send(apiResponse);
//        }
//    }

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

    @GetMapping("/export/excel-url")
    public ResponseEntity<Object> generateExcelOfStudentsDetailsAndProvideUrl() {
        try {
            String fileName = studentService.generateAndSaveExcel();
            String fileUrl = "http://localhost:9090/files/" + fileName;
            System.out.println("file url is" + fileUrl);

            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Excel file generated successfully.")
                    .data(fileUrl)
                    .statusCode(HttpStatus.OK.value())
                    .build();
            return ResponseSender.send(apiResponse);

        } catch (Exception e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Error occurred while generating Excel file.")
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
            return ResponseSender.send(apiResponse);
        }
    }

  /*  @GetMapping("students/files/{filename}")
    public ResponseEntity<?> serveExcelFile(@PathVariable String filename) {
        try {
            byte[] fileContent = studentService.getFileContent(filename);
            MediaType mediaType = filename.endsWith(".xlsx")
                    ? MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    : MediaType.APPLICATION_OCTET_STREAM;

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
            headers.setContentType(mediaType);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileContent);
        } catch (IOException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Error occurred while serving the file.")
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }*/

}




