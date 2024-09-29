package com.employee.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DepartmentDto {
    @NotBlank(message = "Department name must not be null")
    //Ensures that the field is not null and the trimmed length is greater than 0.
    @Size(min = 2, max = 50, message = "DepartmentName must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-z]+$", message = "DepartmentName should only contains letters")
    @Schema(example = "HumanResources", description = "Name of the department")
    private String name;


}
