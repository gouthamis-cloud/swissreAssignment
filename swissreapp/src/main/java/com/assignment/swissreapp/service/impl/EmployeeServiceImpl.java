package com.assignment.swissreapp.service.impl;

import com.assignment.swissreapp.model.Employee;
import com.assignment.swissreapp.service.IEmployeeService;
import com.assignment.swissreapp.util.ExcelReaderUtil;
import com.assignment.swissreapp.util.ExcelWriterUtil;
import com.assignment.swissreapp.util.HierarchyUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Override
    public List<Employee> readAllEmployees(MultipartFile file) throws IOException {
        return ExcelReaderUtil.readEmployeesFromExcel(file.getBytes());
    }

    @Override
    public List<Employee> getGratuityEligibleEmployees(MultipartFile file) throws IOException {
        List<Employee> employees = readAllEmployees(file);
        return employees.stream()
                .filter(e -> e.getDoj().plusMonths(60).isBefore(LocalDate.now()))
                .toList();
    }

    @Override
    public List<Employee> getEmployeesWithHigherSalaryThanManager(MultipartFile file) throws IOException {
        List<Employee> employees = readAllEmployees(file);
        Map<Long, Employee> idToEmp = employees.stream()
                .collect(Collectors.toMap(Employee::getId, e -> e));

        return employees.stream()
                .filter(e -> e.getManagerId() != null)
                .filter(e -> {
                    Employee mgr = idToEmp.get(e.getManagerId());
                    return mgr != null && e.getSalary() > mgr.getSalary();
                })
                .toList();
    }

    @Override
    public List<Map<String, Object>> getEmployeeHierarchyTree(MultipartFile file) throws IOException {
        List<Employee> employees = readAllEmployees(file);
        return HierarchyUtil.buildHierarchy(employees);
    }

    @Override
    public String saveHierarchyJson(MultipartFile file) throws IOException {
        List<Map<String, Object>> hierarchy = getEmployeeHierarchyTree(file);
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File("employee_hierarchy.json"), hierarchy);
        return "Hierarchy written to employee_hierarchy.json";
    }

    @Override
    public ByteArrayInputStream exportAllEmployeesToExcel(MultipartFile file) throws IOException {
        List<Employee> employees = readAllEmployees(file);
        return ExcelWriterUtil.writeEmployeesToExcel(employees);
    }
}
