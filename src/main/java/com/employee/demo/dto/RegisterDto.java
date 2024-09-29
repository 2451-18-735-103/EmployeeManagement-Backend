package com.employee.demo.dto;

import com.employee.demo.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {


    //Set<Role> roles = new HashSet<>();
    private String username;
    private String password;
    private long employeeId;
    private Role role;

}
