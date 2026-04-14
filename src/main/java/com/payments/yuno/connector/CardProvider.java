package com.payments.yuno.connector;

import com.payments.yuno.entity.Payment;
import com.payments.yuno.enums.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public class CardProvider {

    public Payment processCardPayment(Payment payment) {
        // just simulating random behavior
        if (Math.random() < 0.7) {
            payment.setStatus(PaymentStatus.SUCCESS);
        } else {
            throw new RuntimeException("Provider A failed");
        }
        payment.setProvider("A");
        return payment;
    }
}