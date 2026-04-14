package com.payments.yuno.service;

import com.payments.yuno.entity.Payment;
import com.payments.yuno.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IdempotencyService {

    private final PaymentRepository repository;

    public IdempotencyService(PaymentRepository repository) {
        this.repository = repository;
    }

    public Optional<Payment> check(String key) {
        if (key == null || key.isBlank()) {
            return Optional.empty();
        }
        return repository.findByIdempotencyKey(key);
    }
}
