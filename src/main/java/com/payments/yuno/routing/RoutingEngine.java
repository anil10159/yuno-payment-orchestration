package com.payments.yuno.routing;

import com.payments.yuno.entity.Payment;
import com.payments.yuno.enums.PaymentMethod;
import com.payments.yuno.enums.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public class RoutingEngine {

    private final PaymentExecutor paymentExecutor;

    public RoutingEngine(PaymentExecutor paymentExecutor) {
        this.paymentExecutor = paymentExecutor;
    }

    public Payment route(Payment payment) {

        try {
            if (payment.getMethod() == PaymentMethod.CARD) {
                return paymentExecutor.processCard(payment);
            } else {
                return paymentExecutor.processUpi(payment);
            }
        } catch (Exception ex) {
            payment.setStatus(PaymentStatus.FAILED);
            return payment;
        }
    }
}