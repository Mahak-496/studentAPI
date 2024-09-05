package com.example.studentapi.student.dto.response;

import com.example.studentapi.standard.dto.Response.StandardResponseDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentResponseDTO {

    private int id;
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
    private String school;
    private String subjects;
    private StandardResponseDTO standard;

}
