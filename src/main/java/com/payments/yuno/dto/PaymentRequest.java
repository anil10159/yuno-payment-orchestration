package com.payments.yuno.dto;

import com.payments.yuno.enums.PaymentMethod;
import lombok.Data;

@Data
public class PaymentRequest {
    private String userId;
    private Double amount;
    private String currency;
    private PaymentMethod method;
}
