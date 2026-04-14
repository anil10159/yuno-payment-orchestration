package com.payments.yuno.connector;

import com.payments.yuno.entity.Payment;
import com.payments.yuno.enums.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public class UPIProvider {

    public Payment processUpiPayment(Payment payment) {
        // assuming UPI is stable here
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setProvider("B");
        return payment;
    }
}