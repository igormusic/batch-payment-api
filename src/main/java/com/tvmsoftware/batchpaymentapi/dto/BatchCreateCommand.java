package com.tvmsoftware.batchpaymentapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class BatchCreateCommand {
    private String batchId;
    private List<PaymentCreateCommand> payments;
}
