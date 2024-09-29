package com.employee.demo.dto;

import com.employee.demo.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//for outgoing response we use this
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
    private long employeeId;
    private String firstName;
    private String lastName;

    private String mobile;
    private String email;
    private long departmentId;
    private User user;

}
