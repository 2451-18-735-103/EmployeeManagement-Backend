package com.employee.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)//api will return not found exception to client
public class Emp extends RuntimeException{

    public Emp(String message){
        super(message);
    }
}
