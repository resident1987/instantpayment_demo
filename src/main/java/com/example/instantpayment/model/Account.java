package com.example.instantpayment.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Data
public class Account {
  @Id private Long id;
  @Column(nullable = false) private BigDecimal balance;
  @Column(nullable = false) private String currency;
}