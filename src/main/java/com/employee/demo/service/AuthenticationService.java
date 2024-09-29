package com.employee.demo.service;

import com.employee.demo.dto.AdminRegisterDto;
import com.employee.demo.dto.JwtTokenResponse;
import com.employee.demo.dto.LoginDto;
import com.employee.demo.model.Role;

import java.util.List;

public interface AuthenticationService {
    // String signUp(RegisterDto registerDto);

    JwtTokenResponse signIn(LoginDto loginRequest);

    String addRole(Role role);


    String registerAdmin(AdminRegisterDto adminRegisterDto);

    List<Role> findAllRoles();
}
