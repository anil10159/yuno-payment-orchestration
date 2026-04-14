package com.payments.yuno.connector;

import com.payments.yuno.entity.Payment;
import com.payments.yuno.enums.PaymentStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UPIProviderTest {

    private final UPIProvider provider = new UPIProvider();

    @Test
    void shouldAlwaysReturnSuccess() {

        Payment payment = new Payment();

        Payment result = provider.processUpiPayment(payment);

        assertEquals(PaymentStatus.SUCCESS, result.getStatus());
        assertEquals("B", result.getProvider());
    }
}