package com.example.instantpayment.controller;

import com.example.instantpayment.dto.PaymentRequest;
import com.example.instantpayment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
  private final PaymentService paymentService;
  public PaymentController(PaymentService paymentService) { this.paymentService = paymentService; }

  @Operation(summary = "Make payment", description = "Return 200 if the payment success.")
  @ApiResponses({
          @ApiResponse(responseCode = "400", description = "Payment Exception", content = @Content(mediaType = "application/json")),
  })
  @PostMapping
  public ResponseEntity<String> makePayment(@RequestBody PaymentRequest req) {
    paymentService.processPayment(req);
    return ResponseEntity.ok("Payment processed");
  }
}