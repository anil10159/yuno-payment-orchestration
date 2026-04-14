package com.payments.yuno.connector;

import com.payments.yuno.entity.Payment;
import com.payments.yuno.enums.PaymentStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardProviderTest {

    private final CardProvider provider = new CardProvider();

    @Test
    void shouldReturnSuccessOrThrowException() {

        Payment payment = new Payment();

        try {
            Payment result = provider.processCardPayment(payment);

            // if success, verify status
            assertEquals(PaymentStatus.SUCCESS, result.getStatus());
            assertEquals("A", result.getProvider());

        } catch (RuntimeException ex) {
            // failure is expected sometimes due to randomness
            assertEquals("Provider A failed", ex.getMessage());
        }
    }
}