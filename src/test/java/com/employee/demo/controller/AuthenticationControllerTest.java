package com.employee.demo.controller;

import com.employee.demo.dto.AdminRegisterDto;
import com.employee.demo.dto.JwtTokenResponse;
import com.employee.demo.dto.LoginDto;
import com.employee.demo.model.Role;
import com.employee.demo.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService service;

    @InjectMocks
    private AuthenticationController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddRole() {
        Role dto = new Role();
        dto.setName("ROLE_ADMIN");
        // when(service.addRole(dto)).thenReturn("Role Has Been Added");

        ResponseEntity<String> result = controller.addRole(dto);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void testSignIn() {

        LoginDto login = new LoginDto();
        login.setUsername("manasa");
        login.setPassword("Manasa@1");
        // when(service.addRole(dto)).thenReturn("Role Has Been Added");

        ResponseEntity<JwtTokenResponse> result = controller.signIn(login);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testRegisterAdmin() {

        AdminRegisterDto register = new AdminRegisterDto();
        register.setUsername("manasa");
        register.setPassword("Manasa@1");
        Role role = new Role(1, "ROLE_ADMIN");
        register.setRole(role);
        // when(service.addRole(dto)).thenReturn("Role Has Been Added");

        ResponseEntity<String> result = controller.registerAdmin(register);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }


}
