package com.example.studentapi.utils;
import com.example.studentapi.student.entity.Student;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentExcelExporter {

    private final XSSFWorkbook workbook;
    private final Sheet sheet;
    private final List<Student> students;

    public StudentExcelExporter(List<Student> students) {
        this.students = students;
        this.workbook = new XSSFWorkbook();
        this.sheet = workbook.createSheet("Student Report");
    }

    private void writeTitle() {
        Row titleRow = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

        createCell(titleRow, 0, "Student Details", style);
    }

    private void writeHeader() {
        Row headerRow = sheet.createRow(1);
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        createCell(headerRow, 0, "Id No", style);
        createCell(headerRow, 1, "Name", style);
        createCell(headerRow, 2, "Email", style);
        createCell(headerRow, 3, "Address", style);
        createCell(headerRow, 4, "Phone Number", style);
        createCell(headerRow, 5, "Standard", style);
    }

    private void createCell(Row row, int columnIndex, Object value, CellStyle style) {
        Cell cell = row.createCell(columnIndex);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeData() {
        int rowCount = 2;

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        for (Student student : students) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, student.getId(), style);
            createCell(row, columnCount++, student.getName() != null ? student.getName() : "", style);
            createCell(row, columnCount++, student.getEmail() != null ? student.getEmail() : "", style);
            createCell(row, columnCount++, student.getAddress() != null ? student.getAddress() : "", style);
            createCell(row, columnCount++, student.getPhoneNumber() != null ? student.getPhoneNumber() : "", style);
            createCell(row, columnCount++, student.getStandard() != null ? student.getStandard().getName() : "", style);
        }
    }

    private void writeSummary() {
        int rowCount = sheet.getLastRowNum() + 2;

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);

        Row summaryRow = sheet.createRow(rowCount);
        int columnCount = 0;

        createCell(summaryRow, columnCount++, "Total Number of Students:", style);
        createCell(summaryRow, columnCount++, students.size(), style);

        Set<String> standards = new HashSet<>();
        for (Student student : students) {
            if (student.getStandard() != null) {
                standards.add(student.getStandard().getName());
            }
        }
        summaryRow = sheet.createRow(++rowCount);
        columnCount = 0;
        createCell(summaryRow, columnCount++, "Total Number of Standards:", style);
        createCell(summaryRow, columnCount++, standards.size(), style);
    }

    public void export(OutputStream outputStream) throws IOException {
        writeTitle();
        writeHeader();
        writeData();
        writeSummary();
        workbook.write(outputStream);
        workbook.close();
    }
}
