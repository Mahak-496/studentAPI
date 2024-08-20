package com.example.studentapi.utils;

import com.example.studentapi.standard.entity.Standard;
import com.example.studentapi.student.dto.response.StudentResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Component
public class CsvFileGenerator {
    private final ObjectMapper objectMapper = new ObjectMapper();
    public void writeStudentsToCsv(List<StudentResponseDTO> students, Writer writer) {
        try {
            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("ID", "Name", "Email", "Address", "Phone Number", "School", "Standard", "Subjects"));
//                    .withQuoteMode(org.apache.commons.csv.QuoteMode.ALL));

            for (StudentResponseDTO student : students) {
                String standardJson = objectMapper.writeValueAsString(student.getStandard());
                String subjects = String.join(", ", student.getSubjects());

                printer.printRecord(
                        student.getId(),
                        student.getName(),
                        student.getEmail(),
                        student.getAddress(),
                        student.getPhoneNumber(),
                        student.getSchool(),
                        standardJson,
                        subjects

                );
            }

            printer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
