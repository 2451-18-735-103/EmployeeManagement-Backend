package com.employee.demo.dto;

import com.employee.demo.model.Employee;
import lombok.Data;

import java.util.List;

@Data
public class DepartmentResponse {
    private Long departmentId;
    private String name;
    //    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
}
