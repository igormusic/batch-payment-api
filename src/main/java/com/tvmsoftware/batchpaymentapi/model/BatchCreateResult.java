package com.tvmsoftware.batchpaymentapi.model;

import lombok.Data;

@Data
public class BatchCreateResult {
    private Integer rows;
    private String error;
}
