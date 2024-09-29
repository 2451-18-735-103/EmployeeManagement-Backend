package com.employee.demo.service;

import com.employee.demo.dto.EmployeeDto;
import com.employee.demo.dto.EmployeeResponse;
import com.employee.demo.exception.DepartmentNotFoundException;
import com.employee.demo.exception.EmployeeNotFoundException;
import com.employee.demo.exception.RoleNotFoundException;
import com.employee.demo.exception.UserAlreadyExistsException;
import com.employee.demo.model.Department;
import com.employee.demo.model.Employee;
import com.employee.demo.model.Role;
import com.employee.demo.model.User;
import com.employee.demo.repository.DepartmentRepository;
import com.employee.demo.repository.EmployeeRepository;
import com.employee.demo.repository.RoleRepository;
import com.employee.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j

@Service
public class EmployeeServiceImpl implements EmployeeService {


    private final ModelMapper mapper;

    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(ModelMapper mapper, EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.mapper = mapper;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public String registerEmployee(EmployeeDto employeeDto) {

        // Check if username already exists
        if (userRepository.findByUsername(employeeDto.getUser().getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username already exists: " + employeeDto.getUser().getUsername());
        }

        // Create and set the User object
        User user = new User();
        user.setUsername(employeeDto.getUser().getUsername());
        user.setPassword(passwordEncoder.encode(employeeDto.getUser().getPassword()));

        // Fetch and set the role
        Role role = roleRepository.findById(employeeDto.getUser().getRoles().getRoleId())
                .orElseThrow(() -> new RoleNotFoundException("Role not found: " + employeeDto.getUser().getRoles().getRoleId()));
        user.setRoles(role);

        // Create and set the Employee object
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setMobile(employeeDto.getMobile());
        employee.setEmail(employeeDto.getEmail());

        // Fetch and set the department
        Optional<Department> department = departmentRepository.findById(employeeDto.getDepartmentId());
        if (department.isPresent()) {
            employee.setDepartment(department.get());
        } else {
            throw new DepartmentNotFoundException("Department not found with Id: " + employeeDto.getDepartmentId());
        }

        // Associate user with employee
        employee.setUser(user);

        // Save the user and employee entities to the database
        userRepository.save(user);
        employeeRepository.save(employee);

        return "Employee Registered Successfully";
    }


    @Override
    public EmployeeResponse findEmployeeById(Long id) {
        log.info("Finding employee by id {}", id);
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            EmployeeResponse response = mapper.map(optionalEmployee.get(), EmployeeResponse.class);
            if (optionalEmployee.get().getDepartment() != null) {
                response.setDepartmentId(optionalEmployee.get().getDepartment().getDepartmentId());
            }
            log.info("Employee found successfully with id {}", id);
            return response;
        } else {
            log.error("Employee not found with id: {}", id);
            throw new EmployeeNotFoundException("Employee Not Found with id: " + id);
        }
    }

    @Override
    public EmployeeResponse updateEmployeeById(Long id, EmployeeDto employeeDto) {
        log.info("updating employee with given id {}", id);
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee emp = optionalEmployee.get();
            emp.setEmployeeId(id);
            emp.setFirstName(employeeDto.getFirstName());
            emp.setLastName(employeeDto.getLastName());
            emp.setEmail(employeeDto.getEmail());
            emp.setMobile(employeeDto.getMobile());
            Department department = departmentRepository.findById(employeeDto.getDepartmentId())
                    .orElseThrow(() -> new DepartmentNotFoundException("Department Not Found with id: " + employeeDto.getDepartmentId()));
            emp.setDepartment(department);
            Employee updatedEmployee = employeeRepository.save(emp);
            EmployeeResponse response = mapper.map(updatedEmployee, EmployeeResponse.class);
            response.setDepartmentId(updatedEmployee.getDepartment().getDepartmentId());
            log.info("Employee updated successfully with id {}" + id);
            return response;

        } else {
            log.error("Employee not found with id: {}", id);
            throw new EmployeeNotFoundException("Employee Not Found with id: " + id);
        }
    }

    @Override
    public String deleteEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee emp = optionalEmployee.get();
            employeeRepository.delete(emp);
            return "Employee Deleted successfully with id:" + id;
        } else {
            log.error("Employee not found with id: {}", id);
            throw new EmployeeNotFoundException("Employee Not Found with id: " + id);
        }
    }

    @Override
    public List<EmployeeResponse> findAllEmployees() {
        return employeeRepository.findAll().stream().map(employee -> {
            EmployeeResponse response = mapper.map(employee, EmployeeResponse.class);
            if (employee.getDepartment() != null) {
                response.setDepartmentId(employee.getDepartment().getDepartmentId());
            }
            return response;
        }).toList();
    }
}
