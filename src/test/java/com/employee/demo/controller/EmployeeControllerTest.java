package com.employee.demo.controller;


import com.employee.demo.dto.EmployeeDto;
import com.employee.demo.dto.EmployeeResponse;
import com.employee.demo.model.Department;
import com.employee.demo.model.Employee;
import com.employee.demo.model.Role;
import com.employee.demo.model.User;
import com.employee.demo.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmployeeControllerTest {
    @Mock
    private EmployeeServiceImpl employeeServiceMock;
    @InjectMocks
    private EmployeeController employeeControllerMock;
    private Role role;
    private User user;
    private Department department;
    private EmployeeDto employeeDto;
    private Employee employee;
    private EmployeeResponse employeeResponse;

    @BeforeEach
    void setUp() {
        role = new Role(1L, "ROLE_EMPLOYEE");
        user = new User(1L, "priya", "priya@1", role);
        department = new Department(1L, "HR", null);

        employeeDto = new EmployeeDto(1L, "manasa", "kothakonda", "9381049498", "manasa@gmail.com", 1L, user);
        employee = new Employee(1L, "manasa", "kothakonda", "9381049498", "manasa@gmail.com", department, user);
        employeeResponse = new EmployeeResponse(1L, "manasa", "kothakonda", "9381049498", "manasa@gmail.com", 1L, user);
    }

    @Test
    void testAddEmployee() throws Exception {
//        Role role = new Role(1L, "ROLE_EMPLOYEE");
//        User user = new User(1L, "priya", "priya@1", role);
//        Department department = new Department(1L, "HR", null);
//
//        EmployeeDto employeeDto = new EmployeeDto(1L, "manasa", "kothakonda", "9381049498", "manasa@gmail.com", 1L, user);
//        Employee employee = new Employee(1L, "manasa", "kothakonda", "9381049498", "manasa@gmail.com", department, user);
        when(employeeServiceMock.registerEmployee(employeeDto)).thenReturn("Employee Registered Successfully");
        ResponseEntity<String> result = employeeControllerMock.addEmployee(employeeDto);
        assertNotNull(result);
        assertEquals("Employee Registered Successfully", result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(employeeServiceMock, times(1)).registerEmployee(employeeDto);
    }

    @Test
    void testFindEmployeeById() throws Exception {
        when(employeeServiceMock.findEmployeeById(1L)).thenReturn(employeeResponse);
        ResponseEntity<EmployeeResponse> result = employeeControllerMock.findEmployeeById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(employeeResponse, result.getBody());
        verify(employeeServiceMock, times(1)).findEmployeeById(1L);

    }

//    @Test
//    void testUpdatefindEmployeeById(1L)).thenReturn(employeeResponse);
//    ResponseEntity<EmployeeResponse> result = employeeControllerMock.findEmployeeById(1L);
//
//    assertEquals(HttpStatus.OK, result.getStatusCode());
//    assertEquals(employeeResdateEmployeeById() throws Exception {
//        when(employeeServiceMoponse, result.getBody());
//        verify(employeeServiceMock, times(1)).findEmployeeById(1L);
//
//    }
}
