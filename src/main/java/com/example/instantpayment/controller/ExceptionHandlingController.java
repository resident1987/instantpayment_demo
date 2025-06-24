package com.example.instantpayment.controller;

import com.example.instantpayment.dto.ErrorObject;
import com.example.instantpayment.exception.PaymentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {


    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorObject> handlePaymentException(PaymentException ex) {
        return new ResponseEntity<>(new ErrorObject(ex.getMessage()), HttpStatus.CONFLICT);
    }

}