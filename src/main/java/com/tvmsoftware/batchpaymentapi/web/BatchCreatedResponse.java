package com.tvmsoftware.batchpaymentapi.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BatchCreatedResponse {
    private String batchId;
    private Integer rowsImported;
    private String message;
}
