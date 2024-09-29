package com.employee.demo.controller;

import com.employee.demo.dto.DepartmentDto;
import com.employee.demo.dto.DepartmentResponse;
import com.employee.demo.model.Department;
import com.employee.demo.model.Employee;
import com.employee.demo.model.User;
import com.employee.demo.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DepartmentControllerTest {
    @Mock
    private DepartmentService service;

    @InjectMocks
    private DepartmentController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdd() {
        DepartmentDto dto = new DepartmentDto();
        dto.setName("HR");

        DepartmentResponse response = new DepartmentResponse();
        response.setDepartmentId(Long.valueOf(1));
        response.setName("HR");

        when(service.createDepartment(dto)).thenReturn(response);

        ResponseEntity<DepartmentResponse> result = controller.add(dto);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void testFindById() {
        DepartmentResponse response = new DepartmentResponse();
        Department d = new Department();
        d.setDepartmentId(1);
        User u = new User();
        u.setId(1);

        Employee e = new Employee(1, "manasa", "kothakonda", "9381049498", "kothakonda@gmail.com", d, u);
        response.setDepartmentId(Long.valueOf(1));
        response.setName("HR");
        response.setEmployees(List.of(e));
        when(service.findDepartmentById(Long.valueOf(1))).thenReturn(response);

        ResponseEntity<DepartmentResponse> result = controller.findById(Long.valueOf(1));
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testFindAll() {
        DepartmentResponse response = new DepartmentResponse();
        response.setDepartmentId(Long.valueOf(1));
        response.setName("HR");

        List<DepartmentResponse> responses = Collections.singletonList(response);
        when(service.findAllDepartments()).thenReturn(responses);

        ResponseEntity<List<DepartmentResponse>> result = controller.findAll();
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testUpdateById() {
        DepartmentDto dto = new DepartmentDto();
        dto.setName("HR");

        DepartmentResponse response = new DepartmentResponse();
        response.setDepartmentId(Long.valueOf(1));
        response.setName("HR");

        when(service.updateDepartmentById(Long.valueOf(1), dto)).thenReturn(response);

        ResponseEntity<DepartmentResponse> result = controller.updateById(Long.valueOf(1), dto);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testDelete() {
        DepartmentResponse response = new DepartmentResponse();
        response.setDepartmentId(Long.valueOf(1));
        response.setName("HR");
        when(service.deleteDepartmentById(Long.valueOf(1))).thenReturn(response);
        ResponseEntity<DepartmentResponse> result = controller.delete(Long.valueOf(1));
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
