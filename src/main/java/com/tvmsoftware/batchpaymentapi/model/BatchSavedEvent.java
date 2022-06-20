package com.tvmsoftware.batchpaymentapi.model;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class BatchSavedEvent extends ApplicationEvent {
    private String batchId;
    public BatchSavedEvent(Object source, String batchId) {
        super(source);
        this.batchId = batchId;
    }
}
