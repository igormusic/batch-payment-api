package com.tvmsoftware.batchpaymentapi.model;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

public interface BatchRepository extends CrudRepository<Batch, String> {
    @Procedure(outputParameterName="message",procedureName = "PAYMENTS.createBatch")
    String createBatch(String batchId, String paymentsJson);
}
