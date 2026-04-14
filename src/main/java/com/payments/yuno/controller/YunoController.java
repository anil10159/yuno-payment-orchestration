package com.payments.yuno.controller;

import com.payments.yuno.dto.PaymentRequest;
import com.payments.yuno.entity.Payment;
import com.payments.yuno.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class YunoController {

    private final PaymentService service;

    public YunoController(PaymentService service) {
        this.service = service;
    }

    @PostMapping("/createPayment")
    public ResponseEntity<Payment> createPayment(
            @RequestHeader(value = "Idempotency-Key", required = false) String key,
            @RequestBody PaymentRequest request) {

        Payment payment = service.createPayment(request, key);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable String id) {
        return ResponseEntity.ok(service.getPayment(id));
    }
}
