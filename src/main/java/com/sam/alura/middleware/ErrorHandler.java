package com.sam.alura.middleware;

import com.sam.alura.exceptions.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErrorHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ObjectError> globalErrors = ex.getGlobalErrors();
        List<FieldError> fieldErrors = ex.getFieldErrors();
        return buildResponse(globalErrors, fieldErrors);
    }

    @ExceptionHandler(AppException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleAppException(AppException ex) {
        return ex.getMessage();
    }
    public ErrorResponse buildResponse(List<ObjectError> globalErrors, List<FieldError> fieldErrors ) {
        ErrorResponse errorResponse = new ErrorResponse();
        globalErrors.forEach(err -> errorResponse.addGlobalError(err.getDefaultMessage()));
        fieldErrors.forEach(err -> errorResponse.addFieldError(err.getField(), err.getDefaultMessage()));
        return errorResponse;
    }
}
