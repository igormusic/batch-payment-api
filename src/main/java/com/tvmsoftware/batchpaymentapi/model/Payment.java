package com.tvmsoftware.batchpaymentapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@Table(schema = "PAYMENTS")
public class Payment {
    @Id
    private String paymentId;
    private String batchId;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    private String paymentInstruction;
    private LocalDateTime createdOn;
    private LocalDateTime lastUpdatedOn;
    private String error;

    public Payment() {

    }

    public void processPayment() {
        paymentStatus = PaymentStatus.COMPLETED;
        lastUpdatedOn = LocalDateTime.now();
    }

    public void processPaymentError(String error) {
        this.error = error;
        paymentStatus = PaymentStatus.ERROR;
        lastUpdatedOn = LocalDateTime.now();
    }
}
