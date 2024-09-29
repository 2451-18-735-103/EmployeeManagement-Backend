package com.employee.demo.utils;

import com.employee.demo.dto.DepartmentDto;
import com.employee.demo.dto.DepartmentResponse;
import com.employee.demo.dto.EmployeeDto;
import com.employee.demo.dto.EmployeeResponse;
import com.employee.demo.model.Department;
import com.employee.demo.model.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MapperUtil {
    private final static ModelMapper mapper;

    static {
        mapper = new ModelMapper();
    }

    //Employee(entityToDto)
    //This method will convert Employee entity to EmployeeDto object
    //it is using the modelmapper instance to map the properties of employee object to a new instance of EmployeeDto
    public static EmployeeDto entityToDto(Employee employee) {

        return mapper.map(employee, EmployeeDto.class);
    }

    //Employee(dtoToEntity)
    //map the properties of dto object to new instance of Employee
    public static Employee dtoToEntity(EmployeeDto dto) {
//        Employee e=new Employee();
//        e.setFirstName(dto.getFirstName());
//        e.setLastName(dto.getLastName());
//        e.setEmail(dto.getEmail());
//        e.setMobile(dto.getMobile());
//
//        return e;
        return mapper.map(dto, Employee.class);

    }

    //Department(dtoToEntity)
    //map the properties of department dto to department entity
    public static Department dtoToEntity(DepartmentDto dto) {
        return mapper.map(dto, Department.class);

    }

    //Department Output
    //This method will converts department entity to an DepartmentResponse DTO
    public static DepartmentResponse departmentOutput(Department dept) {
        return mapper.map(dept, DepartmentResponse.class);
    }

    //EmployeeOutput
    //This method will converts employee entity to an EmployeeResponse DTO
    public static EmployeeResponse employeeOutput(Employee employee) {
//        EmployeeResponse emp=new EmployeeResponse();
//        emp.setId(employee.getId());
//        emp.setFirstName(employee.getFirstName());
//        emp.setLastName(employee.getLastName());
//        emp.setEmail(employee.getEmail());
//        emp.setMobile(employee.getMobile());
//        return emp;
        EmployeeResponse response = mapper.map(employee, EmployeeResponse.class);
        response.setDepartmentId(employee.getDepartment().getDepartmentId());
        return response;
    }


}
