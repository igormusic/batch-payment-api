package com.tvmsoftware.batchpaymentapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Table(schema = "PAYMENTS")
public class Batch {
    @Id
    private String batchId;
    private LocalDateTime createdOn;
    private LocalDateTime lastUpdatedOn;
    @Enumerated(EnumType.STRING)
    private BatchStatus batchStatus;

    public Batch() {

    }

    public Batch(String batchId) {
        this.batchId = batchId;
        this.batchStatus = BatchStatus.PENDING;
        this.createdOn = LocalDateTime.now();
    }

    public void markComplete() {
        this.batchStatus = BatchStatus.COMPLETED_SUCCESS;
        this.lastUpdatedOn = LocalDateTime.now();
    }
}
