package com.employee.demo.controller;

import com.employee.demo.dto.AdminRegisterDto;
import com.employee.demo.dto.JwtTokenResponse;
import com.employee.demo.dto.LoginDto;
import com.employee.demo.model.Role;
import com.employee.demo.service.AuthenticationService;
import com.employee.demo.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/roles/add")
    public ResponseEntity<String> addRole(@RequestBody Role role) {
        return new ResponseEntity<>(authenticationService.addRole(role), HttpStatus.CREATED);
    }

//    @PostMapping("/users/signup")
//    public ResponseEntity<String> signUp(@RequestBody RegisterDto registerDto) {
//        return new ResponseEntity<>(authenticationService.signUp(registerDto), HttpStatus.CREATED);
//    }

    @PostMapping("/users/signin")
    public ResponseEntity<JwtTokenResponse> signIn(@RequestBody LoginDto loginRequest) {

        return new ResponseEntity<>(authenticationService.signIn(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/users/admin")
    public ResponseEntity<String> registerAdmin(@RequestBody AdminRegisterDto adminRegisterDto) {
        return new ResponseEntity<>(authenticationService.registerAdmin(adminRegisterDto), HttpStatus.CREATED);
    }

    @GetMapping("/users/username/{username}")
    public ResponseEntity<UserDetails> loadUserByUsername(@PathVariable("username") String username) {
        return new ResponseEntity<>(userDetailsService.loadUserByUsername(username), HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Role>> findAll() {
        return new ResponseEntity<>(authenticationService.findAllRoles(), HttpStatus.OK);
    }
//    @PostMapping("/token/refresh")
//    public ResponseEntity<JwtTokenResponse> refreshAccessToken(@RequestBody JwtTokenResponse tokenResponse) {
//        JwtTokenResponse newTokenResponse = authenticationService.refreshAccessToken(tokenResponse.getRefreshToken());
//        return new ResponseEntity<>(newTokenResponse, HttpStatus.OK);
//    }

}
