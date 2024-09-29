package com.employee.demo.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler({EmployeeNotFoundException.class, DepartmentNotFoundException.class, UserNotFoundException.class, RoleNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(HttpServletRequest request, Exception ex) {
        var response = new ErrorResponse();
        response.setUrl(request.getRequestURI());
        response.setMessage(ex.getMessage());
        response.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({EmployeeAlreadyExistsException.class, DepartmentAlreadyExistsException.class, UserAlreadyExistsException.class})
    public ResponseEntity<String> handleAlreadyExistsException(HttpServletRequest request, Exception ex) {
        var response = new ErrorResponse();
        //response.setUrl(request.getRequestURI());
        response.setMessage(ex.getMessage());
        //response.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(response.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    protected ResponseEntity<ErrorResponse> handleSecurityExceptions(InvalidCredentialsException ex,
                                                                     WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), request.getDescription(false), LocalDateTime.now());
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(CustomSecurityException.class)
    public ResponseEntity<ErrorResponse> handleCustomSecurityException(CustomSecurityException ex, WebRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, status);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
