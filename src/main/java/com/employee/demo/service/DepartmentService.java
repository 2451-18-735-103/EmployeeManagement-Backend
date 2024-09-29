package com.employee.demo.service;

import com.employee.demo.dto.DepartmentDto;
import com.employee.demo.dto.DepartmentResponse;

import java.util.List;

public interface DepartmentService {
    DepartmentResponse createDepartment(DepartmentDto dto);

    DepartmentResponse findDepartmentById(Long id);

    DepartmentResponse updateDepartmentById(Long id, DepartmentDto dto);

    DepartmentResponse deleteDepartmentById(Long id);

    List<DepartmentResponse> findAllDepartments();
}
