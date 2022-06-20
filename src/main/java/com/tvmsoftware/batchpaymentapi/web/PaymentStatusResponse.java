package com.tvmsoftware.batchpaymentapi.web;

import com.tvmsoftware.batchpaymentapi.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentStatusResponse {
    private String batchId;
    private String paymentId;
    private PaymentStatus paymentStatus;
    private String error;
}
