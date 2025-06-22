package com.assignment.swissreapp.util;

import com.assignment.swissreapp.model.Employee;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class ExcelWriterUtil {

    public static ByteArrayInputStream writeEmployeesToExcel(List<Employee> employees) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Employees");

            String[] headers = {"ID", "Name", "City", "State", "Category", "Manager ID", "Salary", "DOJ"};
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < headers.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(headers[col]);
            }

            int rowIdx = 1;
            for (Employee emp : employees) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(emp.getId());
                row.createCell(1).setCellValue(emp.getName());
                row.createCell(2).setCellValue(emp.getCity());
                row.createCell(3).setCellValue(emp.getState());
                row.createCell(4).setCellValue(emp.getCategory());
                row.createCell(5).setCellValue(emp.getManagerId() != null ? emp.getManagerId() : 0);
                row.createCell(6).setCellValue(emp.getSalary());
                row.createCell(7).setCellValue(emp.getDoj().toString());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Failed to export employee data", e);
        }
    }
}
