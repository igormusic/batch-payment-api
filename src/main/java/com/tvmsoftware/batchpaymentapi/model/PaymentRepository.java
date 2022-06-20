package com.tvmsoftware.batchpaymentapi.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, String> {
    List<Payment> findPaymentsByBatchIdAndPaymentStatus(String batchId, PaymentStatus paymentStatus);
}
