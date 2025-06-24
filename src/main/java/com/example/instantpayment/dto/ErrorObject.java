package com.example.instantpayment.dto;

import lombok.Data;

@Data
public class ErrorObject {

    private String message;

    public ErrorObject(String message) {
        this.message = message;
    }
}
