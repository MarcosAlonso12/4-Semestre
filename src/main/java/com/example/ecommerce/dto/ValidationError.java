package com.example.ecommerce.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
public class ValidationError extends CustomError {
    private List<FieldMessage> erros = new ArrayList<>();

    public ValidationError(Instant timestamp, Integer status, String erro, String path) {
        super(timestamp, status, erro, path);
    }


    public List<FieldMessage> getErros() {
        return erros;
    }
    public void addError(String fieldName, String message){
        erros.add(new FieldMessage(fieldName, message));
    }
}

