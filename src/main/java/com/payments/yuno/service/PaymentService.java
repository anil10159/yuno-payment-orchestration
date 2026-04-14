package com.payments.yuno.service;

import com.payments.yuno.dto.PaymentRequest;
import com.payments.yuno.entity.Payment;
import com.payments.yuno.enums.PaymentStatus;
import com.payments.yuno.repository.PaymentRepository;
import com.payments.yuno.routing.RoutingEngine;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository repository;
    private final RoutingEngine routingEngine;
    private final IdempotencyService idempotencyService;

    public PaymentService(PaymentRepository repository,
                          RoutingEngine routingEngine,
                          IdempotencyService idempotencyService) {
        this.repository = repository;
        this.routingEngine = routingEngine;
        this.idempotencyService = idempotencyService;
    }

    public Payment createPayment(PaymentRequest request, String idempotencyKey) {

        // check duplicate request
        Optional<Payment> existing = idempotencyService.check(idempotencyKey);
        if (existing.isPresent()) {
            return existing.get();
        }

        Payment payment = new Payment();
        payment.setUserId(request.getUserId());
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setMethod(request.getMethod());
        payment.setStatus(PaymentStatus.PROCESSING);
        payment.setIdempotencyKey(idempotencyKey);
        payment.setCreatedAt(LocalDateTime.now());

        repository.save(payment);

        Payment processed = routingEngine.route(payment);

        return repository.save(processed);
    }

    public Payment getPayment(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
}