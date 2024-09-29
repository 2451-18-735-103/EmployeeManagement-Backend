package com.employee.demo.repository;

import com.employee.demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartmentDepartmentId(Long id);

    //Employee findByUserName(String userName);

//    Optional<Employee> findByEmail(String emailId);
//
//    Optional<Employee> findByMobile(String mobile);
//
//    List<Employee> findByDepartmentId(Long deptId);
}
