package com.employee.demo.exception;

public class DepartmentAlreadyExistsException extends RuntimeException{
    public DepartmentAlreadyExistsException(String message){
        super(message);
    }
}
