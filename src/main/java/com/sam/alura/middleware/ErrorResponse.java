package com.sam.alura.middleware;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {

    public List<String> globalErrors = new ArrayList<>();
    public List<ErrorOutputResponse> fieldErrors = new ArrayList<>();

    public void addGlobalError(String error) {
        globalErrors.add(error);
    }

    public void addFieldError(String field, String message) {
        fieldErrors.add(new ErrorOutputResponse(field, message));
    }
}
