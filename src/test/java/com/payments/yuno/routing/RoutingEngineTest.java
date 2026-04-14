package com.payments.yuno.routing;

import com.payments.yuno.entity.Payment;
import com.payments.yuno.enums.PaymentMethod;
import com.payments.yuno.connector.CardProvider;
import com.payments.yuno.connector.UPIProvider;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class RoutingEngineTest {

    @Test
    void shouldRouteToCardProvider() {

        CardProvider cardProvider = mock(CardProvider.class);
        UPIProvider upiProvider = mock(UPIProvider.class);

        RoutingEngine engine = new RoutingEngine(new PaymentExecutor(cardProvider, upiProvider));

        Payment payment = new Payment();
        payment.setMethod(PaymentMethod.CARD);

        engine.route(payment);

        verify(cardProvider, atLeastOnce()).processCardPayment(any());
    }
}