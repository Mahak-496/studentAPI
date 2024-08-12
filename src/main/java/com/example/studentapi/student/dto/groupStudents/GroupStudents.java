package com.example.studentapi.student.dto.groupStudents;

import com.example.studentapi.student.dto.response.StudentResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupStudents {

    int standardId;

    List<StudentResponseDTO> students;
}
