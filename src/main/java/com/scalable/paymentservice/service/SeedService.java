package com.scalable.paymentservice.service;

import com.scalable.paymentservice.entity.PaymentRecord;
import com.scalable.paymentservice.repository.PaymentsRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SeedService {

    private final PaymentsRepository repository;

    public SeedService(PaymentsRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void loadPaymentsFromCsv() {
        if (repository.count() > 0) return;

        List<PaymentRecord> paymentsToSave = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource("etsr_payments.csv").getInputStream()))) {

            reader.lines().skip(1).forEach(line -> {
                String[] row = line.split(",");

                PaymentRecord payment = new PaymentRecord();
                payment.setPaymentId(Long.parseLong(row[0]));
                payment.setOrderId(Long.parseLong(row[1]));
                payment.setAmount(new BigDecimal(row[2]));
                payment.setMethod(row[3]);
                payment.setStatus(row[4]);
                payment.setReference(row[5]);
                payment.setCreatedAt(LocalDateTime.parse(row[6], formatter));

                paymentsToSave.add(payment);
            });

            repository.saveAll(paymentsToSave);
            log.info("Seeded " + paymentsToSave.size() + " payment records from CSV successfully.");

        } catch (Exception e) {
            log.error("Failed to seed payment records from CSV", e);
        }
    }
}