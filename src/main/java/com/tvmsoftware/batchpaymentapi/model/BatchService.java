package com.tvmsoftware.batchpaymentapi.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tvmsoftware.batchpaymentapi.dto.BatchCreateCommand;
import com.tvmsoftware.batchpaymentapi.web.BatchCreatedResponse;

import java.util.List;

public interface BatchService {
    BatchCreateResult create(BatchCreateCommand command) throws JsonProcessingException;
    void processPayments(Batch batch, List<Payment> payments);
}
