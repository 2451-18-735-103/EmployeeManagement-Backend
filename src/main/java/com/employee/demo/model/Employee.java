package com.employee.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long employeeId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mobile_No")
    private String mobile;

    @Column(name = "email_Id")
    private String email;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departmentId")
    @JsonBackReference
    private Department department;
    
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;


}
