package com.example.instantpayment.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface CurrencyConverter {

    BigDecimal convert(String from, String to, BigDecimal amount);
}
