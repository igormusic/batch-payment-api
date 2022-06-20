package com.tvmsoftware.batchpaymentapi.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvmsoftware.batchpaymentapi.dto.BatchCreateCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BatchServiceImpl implements BatchService {
    private final BatchRepository batchRepository;
    private final PaymentRepository paymentRepository;
    @Value("${app.threads}")
    private int threads;

    public BatchServiceImpl(BatchRepository repository, PaymentRepository paymentRepository) {
        this.batchRepository = repository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public BatchCreateResult create(BatchCreateCommand command) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String resultString = batchRepository.createBatch(command.getBatchId(), mapper.writeValueAsString(command.getPayments()));
        BatchCreateResult result = mapper.readValue(resultString, BatchCreateResult.class);

        if (result.getError() != null && !result.getError().isEmpty())
            log.error(result.getError());

        return result;
    }

    @Override
    public void processPayments(Batch batch, List<Payment> payments) {

        ExecutorService executorService = Executors.newFixedThreadPool(threads);

        List<Callable<Void>> callables = Arrays
            .stream(payments.toArray())
            .map(
                pmt -> (Callable<Void>)
                    (
                        () ->
                            {
                                this.processPayment((Payment) pmt);
                                return null;
                            }
                    )
                )
                .collect(Collectors.toList());

        try {
            executorService
                .invokeAll(callables)
                .forEach(future -> {
                    try {
                        future.get();
                    }
                    catch (Exception e) {
                        log.error("error processing payment", e);
                        }
                    }
                );
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }

        batch.markComplete();
        batchRepository.save(batch);
        log.info("processed batch " + batch.getBatchId());
    }

    @Override
    public Optional<Batch> getById(String batchId) {
        return batchRepository.findById(batchId);
    }

    private void processPayment(Payment payment) {
        //TODO:Call API
        payment.processPayment();
        paymentRepository.save(payment);
        log.info("processed payment id" + payment.getPaymentId());
    }
}
