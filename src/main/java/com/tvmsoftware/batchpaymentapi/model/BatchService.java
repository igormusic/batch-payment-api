package com.tvmsoftware.batchpaymentapi.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tvmsoftware.batchpaymentapi.dto.BatchCreateCommand;

import java.util.List;
import java.util.Optional;

public interface BatchService {
    BatchCreateResult create(BatchCreateCommand command) throws JsonProcessingException;
    void processPayments(Batch batch, List<Payment> payments);
    Optional<Batch> getById(String batchId);
}
