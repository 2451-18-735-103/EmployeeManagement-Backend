package com.employee.demo.service;

import com.employee.demo.dto.AdminRegisterDto;
import com.employee.demo.dto.JwtTokenResponse;
import com.employee.demo.dto.LoginDto;
import com.employee.demo.exception.InvalidCredentialsException;
import com.employee.demo.exception.RoleNotFoundException;
import com.employee.demo.exception.UserAlreadyExistsException;
import com.employee.demo.jwt.JwtToken;
import com.employee.demo.model.Role;
import com.employee.demo.model.User;
import com.employee.demo.repository.EmployeeRepository;
import com.employee.demo.repository.RoleRepository;
import com.employee.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtToken jwtToken;
    @Autowired
    private EmployeeRepository employeeRepository;

//    @Override
//    public String signUp(RegisterDto registerDto) {
//        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
//            throw new UserAlreadyExistsException("Username already exists: " + registerDto.getUsername());
//        }
//        User user = new User();
//        user.setUsername(registerDto.getUsername());
//        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
//
//
//        Role role = roleRepository.findByName(registerDto.getRole().getName())
//                .orElseThrow(() -> new RoleNotFoundException("Role not found: " + registerDto.getRole()));
//
//        user.setRoles(role);
//        //Fetch and set employee
//        Employee employee = employeeRepository.findById(registerDto.getEmployeeId())
//                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with Id: " + registerDto.getEmployeeId()));
//        // user.setEmployee(employee);
//        employee.setUser(user);
//        // Save the user entity to the database
//        userRepository.save(user);
//        return "User Registered Successfully";
//    }

    @Override
    public JwtTokenResponse signIn(LoginDto loginRequest) {
        //Authenticate the user
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            //set the authentication context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //generate JWT token
            String token = jwtToken.generateToken(authentication);
            JwtTokenResponse response = new JwtTokenResponse();
            response.setToken(token);
            return response;
        } catch (BadCredentialsException ex) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }

    @Override
    public String addRole(Role role) {
        roleRepository.save(role);
        return "Role Has Been Added";
    }


    @Override
    public String registerAdmin(AdminRegisterDto adminRegisterDto) {
        if (userRepository.findByUsername(adminRegisterDto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username already exists: " + adminRegisterDto.getUsername());
        }

        User user = new User();
        user.setUsername(adminRegisterDto.getUsername());
        user.setPassword(passwordEncoder.encode(adminRegisterDto.getPassword()));

//        Set<Role> roles = new HashSet<>();
//        for (String roleName : adminRegisterDto.getRoles()) {
//            Role role = roleRepository.findByName(roleName)
//                    .orElseThrow(() -> new RoleNotFoundException("Role not found: " + roleName));
//            roles.add(role);
//        }
//        user.setRoles(roles);
//        userRepository.save(user);
//        return "Admin Registered Successfully";
        // Set the role directly from the AdminRegisterDto
        Role role = adminRegisterDto.getRole(); // Directly use the Role object
        if (role == null || role.getRoleId() == 0) {
            throw new RoleNotFoundException("Role must be provided");
        }

        user.setRoles(role);

        // Save the user entity to the database
        userRepository.save(user);
        return "Admin Registered Successfully";
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }
}
