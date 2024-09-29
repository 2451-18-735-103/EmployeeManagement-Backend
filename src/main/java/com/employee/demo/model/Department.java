package com.employee.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "department")
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long departmentId;

    private String name;
    //orphanRemoval = true:here if i delete department then employees in that department wont get deleted judt near depId in employee table it will show 0
    //orphanRemoval = false:here if i delete department then employees in that department will get deleted
    //Here iam not using cascade since in departmentdto iam not taking employee that means while posting department iam not entering employee details so
    //that's why no need to use cascade operation
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Employee> employees;


}
