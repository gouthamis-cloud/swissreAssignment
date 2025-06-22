package com.assignment.swissreapp.service;

import com.assignment.swissreapp.model.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IEmployeeService {

    List<Employee> getGratuityEligibleEmployees(MultipartFile file) throws IOException;

    List<Employee> getEmployeesWithHigherSalaryThanManager(MultipartFile file) throws IOException;

    List<Map<String, Object>> getEmployeeHierarchyTree(MultipartFile file) throws IOException;

    ByteArrayInputStream exportAllEmployeesToExcel(MultipartFile file) throws IOException;

    String saveHierarchyJson(MultipartFile file) throws IOException;

    List<Employee> readAllEmployees(MultipartFile file) throws IOException;
}
