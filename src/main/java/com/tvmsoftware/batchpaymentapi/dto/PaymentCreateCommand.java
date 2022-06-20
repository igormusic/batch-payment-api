package com.tvmsoftware.batchpaymentapi.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.tvmsoftware.batchpaymentapi.model.PaymentType;
import lombok.Data;

@Data
public class PaymentCreateCommand {
    private String paymentId;
    private PaymentType paymentType;
    private JsonNode paymentInstruction;
}
