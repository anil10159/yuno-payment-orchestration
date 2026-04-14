package com.payments.yuno.service;

import com.payments.yuno.dto.PaymentRequest;
import com.payments.yuno.entity.Payment;
import com.payments.yuno.enums.PaymentMethod;
import com.payments.yuno.repository.PaymentRepository;
import com.payments.yuno.routing.RoutingEngine;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    private final PaymentRepository repository = mock(PaymentRepository.class);
    private final RoutingEngine routingEngine = mock(RoutingEngine.class);
    private final IdempotencyService idempotencyService = mock(IdempotencyService.class);

    private final PaymentService service =
            new PaymentService(repository, routingEngine, idempotencyService);

    @Test
    void shouldCreatePaymentSuccessfully() {

        PaymentRequest request = new PaymentRequest();
        request.setUserId("U1");
        request.setAmount(1000.0);
        request.setMethod(PaymentMethod.CARD);

        when(idempotencyService.check("key1")).thenReturn(Optional.empty());

        Payment processed = new Payment();
        when(routingEngine.route(any())).thenReturn(processed);

        when(repository.save(any())).thenReturn(processed);

        Payment result = service.createPayment(request, "key1");

        assertNotNull(result);
        verify(routingEngine, times(1)).route(any());
        verify(repository, atLeastOnce()).save(any());
    }

    @Test
    void shouldReturnExistingPaymentForSameIdempotencyKey() {

        Payment existing = new Payment();

        when(idempotencyService.check("key1"))
                .thenReturn(Optional.of(existing));

        PaymentRequest request = new PaymentRequest();

        Payment result = service.createPayment(request, "key1");

        assertEquals(existing, result);

        // ensure no processing happens
        verify(routingEngine, never()).route(any());
        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenPaymentNotFound() {

        when(repository.findById("invalid"))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.getPayment("invalid"));

        assertEquals("Payment not found", ex.getMessage());
    }
}