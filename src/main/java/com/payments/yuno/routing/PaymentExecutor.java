package com.payments.yuno.routing;

import com.payments.yuno.connector.CardProvider;
import com.payments.yuno.connector.UPIProvider;
import com.payments.yuno.entity.Payment;
import com.payments.yuno.enums.PaymentStatus;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;

@Component
public class PaymentExecutor {

    private final CardProvider cardProvider;
    private final UPIProvider upiProvider;

    public PaymentExecutor(CardProvider cardProvider,
                           UPIProvider upiProvider) {
        this.cardProvider = cardProvider;
        this.upiProvider = upiProvider;
    }

    @Retry(name = "paymentRetry", fallbackMethod = "cardFallback")
    public Payment processCard(Payment payment) {
        return cardProvider.processCardPayment(payment);
    }

    @Retry(name = "paymentRetry", fallbackMethod = "upiFallback")
    public Payment processUpi(Payment payment) {
        return upiProvider.processUpiPayment(payment);
    }

    public Payment cardFallback(Payment payment, Exception ex) {
        payment.setStatus(PaymentStatus.FAILED);
        payment.setProvider("A");
        return payment;
    }

    public Payment upiFallback(Payment payment, Exception ex) {
        payment.setStatus(PaymentStatus.FAILED);
        payment.setProvider("B");
        return payment;
    }
}