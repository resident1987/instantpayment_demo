package com.example.instantpayment.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
  private Long senderId;
  private Long recipientId;
  private BigDecimal amount;
}