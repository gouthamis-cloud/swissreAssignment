package com.assignment.swissreapp;

import com.assignment.swissreapp.model.Employee;
import com.assignment.swissreapp.service.impl.EmployeeServiceImpl;
import com.assignment.swissreapp.util.ExcelReaderUtil;
import com.assignment.swissreapp.util.ExcelWriterUtil;
import com.assignment.swissreapp.util.HierarchyUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private MockMultipartFile mockFile;
    private List<Employee> mockEmployees;

    @BeforeEach
    public void setUp() {
        mockFile = new MockMultipartFile("file", "employees.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new byte[]{});
        mockEmployees = List.of(
                new Employee(123L, "Ravi", "Hyderabad", "Telangana", "employee", 456L, 80000, LocalDate.of(2018, 6, 1)),
                new Employee(456L, "Shivam", "Bangalore", "Karnataka", "manager", 789L, 75000, LocalDate.of(2020, 7, 1)),
                new Employee(789L, "Rama", "Chennai", "Tamilnadu", "director", null, 150000, LocalDate.of(2015, 10, 1))
        );
        employeeService = new EmployeeServiceImpl();
    }

    @Test
    public void testGetGratuityEligibleEmployees() throws Exception {
        try (MockedStatic<ExcelReaderUtil> mocked = Mockito.mockStatic(ExcelReaderUtil.class)) {
            mocked.when(() -> ExcelReaderUtil.readEmployeesFromExcel(Mockito.any())).thenReturn(mockEmployees);
            List<Employee> result = employeeService.getGratuityEligibleEmployees(mockFile);
            assertEquals(2, result.size());
        }
    }

    @Test
    public void testGetEmployeesWithHigherSalaryThanManager() throws Exception {
        try (MockedStatic<ExcelReaderUtil> mocked = Mockito.mockStatic(ExcelReaderUtil.class)) {
            mocked.when(() -> ExcelReaderUtil.readEmployeesFromExcel(Mockito.any())).thenReturn(mockEmployees);
            List<Employee> result = employeeService.getEmployeesWithHigherSalaryThanManager(mockFile);
            assertEquals(1, result.size());
            assertEquals("Ravi", result.get(0).getName());
        }
    }

    @Test
    public void testGetEmployeeHierarchyTree() throws Exception {
        try (MockedStatic<ExcelReaderUtil> readerMock = Mockito.mockStatic(ExcelReaderUtil.class);
             MockedStatic<HierarchyUtil> hierarchyMock = Mockito.mockStatic(HierarchyUtil.class)) {

            readerMock.when(() -> ExcelReaderUtil.readEmployeesFromExcel(Mockito.any())).thenReturn(mockEmployees);
            hierarchyMock.when(() -> HierarchyUtil.buildHierarchy(mockEmployees)).thenReturn(List.of(Map.of("id", 789L)));

            List<Map<String, Object>> result = employeeService.getEmployeeHierarchyTree(mockFile);
            assertEquals(1, result.size());
            assertEquals(789L, result.get(0).get("id"));
        }
    }

    @Test
    public void testExportAllEmployeesToExcel() throws Exception {
        try (MockedStatic<ExcelReaderUtil> readerMock = Mockito.mockStatic(ExcelReaderUtil.class);
             MockedStatic<ExcelWriterUtil> writerMock = Mockito.mockStatic(ExcelWriterUtil.class)) {

            readerMock.when(() -> ExcelReaderUtil.readEmployeesFromExcel(Mockito.any())).thenReturn(mockEmployees);
            writerMock.when(() -> ExcelWriterUtil.writeEmployeesToExcel(mockEmployees)).thenReturn(new ByteArrayInputStream(new byte[]{1, 2, 3}));

            ByteArrayInputStream result = employeeService.exportAllEmployeesToExcel(mockFile);
            assertNotNull(result);
        }
    }
}
