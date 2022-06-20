package com.tvmsoftware.batchpaymentapi;

import com.tvmsoftware.batchpaymentapi.model.Batch;
import com.tvmsoftware.batchpaymentapi.model.BatchRepository;
import com.tvmsoftware.batchpaymentapi.model.Payment;
import com.tvmsoftware.batchpaymentapi.model.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = {BatchPaymentApiApplication.class, H2JpaConfig.class})
class BatchPaymentApiApplicationTests {

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void createBatch() {



    }

}
