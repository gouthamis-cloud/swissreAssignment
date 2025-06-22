package com.assignment.swissreapp.util;

import com.assignment.swissreapp.model.Employee;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExcelReaderUtil {

    public static List<Employee> readEmployeesFromExcel(byte[] bytes) {
        List<Employee> employees = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(bytes))) {
            Sheet sheet = workbook.getSheetAt(0);
            boolean isFirstRow = true;
            for (Row row : sheet) {
                if (isFirstRow) { isFirstRow = false; continue; }

                Long id = (long) row.getCell(0).getNumericCellValue();
                String name = row.getCell(1).getStringCellValue();
                String city = row.getCell(2).getStringCellValue();
                String state = row.getCell(3).getStringCellValue();
                String category = row.getCell(4).getStringCellValue();
                Long managerId = (long) row.getCell(5).getNumericCellValue();
                Integer salary = (int) row.getCell(6).getNumericCellValue();
                LocalDate doj = LocalDate.parse(row.getCell(7).getStringCellValue());

                employees.add(new Employee(id, name, city, state, category, managerId, salary, doj));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading Excel", e);
        }
        return employees;
    }
}
