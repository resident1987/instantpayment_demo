package com.example.instantpayment.service;

import com.example.instantpayment.dto.PaymentRequest;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {

    void processPayment(PaymentRequest req);
}
