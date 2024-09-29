package com.employee.demo.service;

import com.employee.demo.dto.EmployeeDto;
import com.employee.demo.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    String registerEmployee(EmployeeDto employeeDto);

    EmployeeResponse findEmployeeById(Long id);

    EmployeeResponse updateEmployeeById(Long id, EmployeeDto employeeDto);

    String deleteEmployeeById(Long id);

    List<EmployeeResponse> findAllEmployees();
}
