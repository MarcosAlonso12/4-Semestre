package com.tads4.ecommerce.dtos.exceptions;

public class FieldMessage {

    private String name;



    private String message;



    public FieldMessage(String name, String message) {
        this.name = name;
        this.message = message;
    }


    public String getMessage() {
        return message;
    }



    public String getName() {
        return name;
    }

}
