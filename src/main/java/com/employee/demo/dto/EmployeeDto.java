package com.employee.demo.dto;

import com.employee.demo.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//for incoming requests we use this
//VALIDATION WORKING IF WE KEEP FOR DTO OTHERVISE IT IS SHOWING INTERNAL SERVER ERROR IF I KEEP
//HERE IT SHOWING PROPER MESSAGE
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private long employeeId;
    @NotBlank(message = "FirstName must not be null")
//Ensures that the field is not null and the trimmed length is greater than 0.
    @Size(min = 2, max = 10, message = "FirstName must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-z]+$", message = "FirstName should only contains letters")
    private String firstName;
    @NotBlank(message = "LastName must not be null")
    @Size(min = 2, max = 10, message = "LastName must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-z]+$", message = "LastName should only contains letters")
    private String lastName;
    //should contain 10 digits
    @Pattern(regexp = "^[0-9]{10}$", message = "Please enter 10 digit Mobile number")
    private String mobile;
    @Email(message = "Please enter valid email id")
    private String email;


    private long departmentId;
    private User user;


}
