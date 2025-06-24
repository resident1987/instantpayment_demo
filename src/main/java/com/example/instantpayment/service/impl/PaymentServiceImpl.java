package com.example.instantpayment.service.impl;
import com.example.instantpayment.exception.PaymentException;
import com.example.instantpayment.service.CurrencyConverter;
import com.example.instantpayment.service.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import com.example.instantpayment.dto.PaymentRequest;
import com.example.instantpayment.model.Account;
import com.example.instantpayment.model.Transaction;
import com.example.instantpayment.repository.AccountRepository;
import com.example.instantpayment.repository.TransactionRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class PaymentServiceImpl implements PaymentService {
  private final AccountRepository accountRepo;
  private final TransactionRepository transactionRepo;
  private final KafkaTemplate<String, String> kafka;
  private final CurrencyConverter converter;
  private final ReentrantLock lock = new ReentrantLock();

  public PaymentServiceImpl(AccountRepository ar, TransactionRepository tr, KafkaTemplate<String, String> kafka, CurrencyConverter converter) {
      this.accountRepo = ar;
      this.transactionRepo = tr;
      this.kafka = kafka;
      this.converter = converter;
  }

  @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 2000))
  @Transactional
  @Override
  public void processPayment(PaymentRequest req) {
    lock.lock();
    try {
      Account sender = accountRepo.findById(req.getSenderId())
              .orElseThrow(() -> new PaymentException("Sender not found: " + req.getSenderId()));
      Account recipient = accountRepo.findById(req.getRecipientId())
              .orElseThrow(() -> new PaymentException("Recipient not found: " + req.getRecipientId()));
      BigDecimal amount = converter.convert(sender.getCurrency(), recipient.getCurrency(), req.getAmount());
      if (sender.getBalance().compareTo(amount) < 0) {
        throw new PaymentException("Insufficient funds: " + amount + " " + recipient.getCurrency());
      }
      sender.setBalance(sender.getBalance().subtract(amount));
      recipient.setBalance(recipient.getBalance().add(amount));
      accountRepo.save(sender);
      accountRepo.save(recipient);
      Transaction tx = new Transaction();
      tx.setSenderId(req.getSenderId());
      tx.setRecipientId(req.getRecipientId());
      tx.setAmount(amount); //recipient account represented the currency
      transactionRepo.save(tx);
      kafka.send("transaction_notifications",
        String.format("Payment %sender -> %recipient : %amount %currency", req.getSenderId(), req.getRecipientId(), amount), recipient.getCurrency());
    } finally {
      lock.unlock();
    }
  }
}