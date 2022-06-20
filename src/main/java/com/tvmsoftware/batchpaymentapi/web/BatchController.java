package com.tvmsoftware.batchpaymentapi.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tvmsoftware.batchpaymentapi.dto.BatchCreateCommand;
import com.tvmsoftware.batchpaymentapi.model.BatchCreateResult;
import com.tvmsoftware.batchpaymentapi.model.BatchSavedEvent;
import com.tvmsoftware.batchpaymentapi.model.BatchService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("batch")
@Slf4j
public class BatchController {

    public BatchController(BatchService service, ApplicationEventPublisher publisher) {
        this.service = service;
        this.publisher = publisher;
    }

    private final BatchService service;
    private final ApplicationEventPublisher publisher;

    @PostMapping
    ResponseEntity<BatchCreatedResponse> createBatch(@RequestBody BatchCreateCommand command) {
        try {
            BatchCreateResult result= service.create(command);

            if (result.getError()!=null && !result.getError().isEmpty()) {
                log.error(result.getError());
                return new ResponseEntity<>(new BatchCreatedResponse(command.getBatchId(), result.getRows(), result.getError()), HttpStatus.BAD_REQUEST);
            }
            else
            {
                log.info("batch "+ command.getBatchId() + " saved");
                publisher.publishEvent(new BatchSavedEvent(this, command.getBatchId()));
                return new ResponseEntity<>(new BatchCreatedResponse( command.getBatchId(), result.getRows(), null), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(new BatchCreatedResponse(command.getBatchId(),0, e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
