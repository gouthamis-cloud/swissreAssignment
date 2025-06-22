package com.assignment.swissreapp.controller;

import com.assignment.swissreapp.model.Employee;
import com.assignment.swissreapp.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/excel")
@RequiredArgsConstructor
public class EmployeeController {

    private final IEmployeeService employeeService;

    @PostMapping("/gratuity-eligible")
    public ResponseEntity<List<Employee>> getGratuityEligible(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(employeeService.getGratuityEligibleEmployees(file));
    }

    @PostMapping("/salary-greater-than-manager")
    public ResponseEntity<List<Employee>> getEmployeesWithHigherSalary(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(employeeService.getEmployeesWithHigherSalaryThanManager(file));
    }

    @PostMapping("/hierarchy-json")
    public ResponseEntity<String> buildHierarchyTree(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(employeeService.saveHierarchyJson(file));
    }

    @PostMapping("/export")
    public ResponseEntity<byte[]> exportUploadedExcel(@RequestParam("file") MultipartFile file) throws IOException {
        ByteArrayInputStream in = employeeService.exportAllEmployeesToExcel(file);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=employees.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(in.readAllBytes());
    }
}
