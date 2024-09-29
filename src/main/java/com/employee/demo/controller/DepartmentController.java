package com.employee.demo.controller;


import com.employee.demo.dto.DepartmentDto;
import com.employee.demo.dto.DepartmentResponse;
import com.employee.demo.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@CrossOrigin("*")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }


    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DepartmentResponse> add(@Valid @RequestBody DepartmentDto dto) {
        // Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // System.out.println(obj);
        return new ResponseEntity<>(service.createDepartment(dto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findDepartmentById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<DepartmentResponse>> findAll() {
        return new ResponseEntity<>(service.findAllDepartments(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponse> updateById(@PathVariable Long id, @RequestBody DepartmentDto dto) {
        return new ResponseEntity<>(service.updateDepartmentById(id, dto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<DepartmentResponse> delete(@PathVariable Long id) {
        return new ResponseEntity<>(service.deleteDepartmentById(id), HttpStatus.OK);
    }
}
