package com.tvmsoftware.batchpaymentapi.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;

@EnableAsync
@Configuration
@Slf4j
public class BatchEventProcessor {

    private final PaymentRepository paymentRepository;
    private final BatchRepository batchRepository;
    private final BatchService batchService;

    public BatchEventProcessor(PaymentRepository paymentRepository, BatchRepository batchRepository, BatchService batchService) {
        this.paymentRepository = paymentRepository;
        this.batchRepository = batchRepository;
        this.batchService = batchService;
    }

    @Async
    @EventListener
    public void onBatchSavedEvent(BatchSavedEvent event) throws Exception {
        log.info("onBatchSavedEvent      : Source : "+event.getSource().getClass().getName() + ", batchId : "+ event.getBatchId());

        try {
            Batch batch = batchRepository.findById(event.getBatchId()).orElseThrow();
            List<Payment> payments = paymentRepository.findPaymentsByBatchIdAndPaymentStatus(event.getBatchId(), PaymentStatus.PENDING);
            batchService.processPayments(batch, payments);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
