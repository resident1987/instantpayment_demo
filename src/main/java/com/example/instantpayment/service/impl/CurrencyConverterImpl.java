package com.example.instantpayment.service.impl;

import com.example.instantpayment.exception.PaymentException;
import com.example.instantpayment.service.CurrencyConverter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CurrencyConverterImpl implements CurrencyConverter {
    @Override
    public BigDecimal convert(String from, String to, BigDecimal amount) {
        if (from == null || from.isEmpty()) {
            throw new PaymentException("from currency is null or empty");
        }
        if (to == null || to.isEmpty()) {
            throw new PaymentException("to currency is null or empty");
        }
        if (from.equals(to)) {
            return amount;
        }
        return convertImpl(from, to, amount);
    }

    private BigDecimal convertImpl(String from, String to, BigDecimal amount) {
        throw new PaymentException("not implemented yet: " + from + " -> " + to);
    }

}
