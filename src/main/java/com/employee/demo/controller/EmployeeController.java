package com.employee.demo.controller;

import com.employee.demo.dto.EmployeeDto;
import com.employee.demo.dto.EmployeeResponse;
import com.employee.demo.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@CrossOrigin("*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/signup")
    public ResponseEntity<String> addEmployee(@Valid @RequestBody EmployeeDto dto) {
        return new ResponseEntity<>(employeeService.registerEmployee(dto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> findEmployeeById(@PathVariable Long id) {
        return new ResponseEntity<>(employeeService.findEmployeeById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('EMPLOYEE')or hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployeeById(@PathVariable Long id, @RequestBody EmployeeDto dto) {
        return new ResponseEntity<>(employeeService.updateEmployeeById(id, dto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long id) {
        return new ResponseEntity<>(employeeService.deleteEmployeeById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<EmployeeResponse>> findAllEmployees() {
        return new ResponseEntity<>(employeeService.findAllEmployees(), HttpStatus.OK);
    }
}

