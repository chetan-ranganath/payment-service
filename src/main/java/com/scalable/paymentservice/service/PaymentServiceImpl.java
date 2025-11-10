package com.scalable.paymentservice.service;


import com.scalable.paymentservice.entity.ChargeRequest;
import com.scalable.paymentservice.entity.PaymentRecord;
import com.scalable.paymentservice.exception.BaseException;
import com.scalable.paymentservice.repository.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;


@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentsRepository repository;

    @Override
    public PaymentRecord chargePayment(ChargeRequest request) {
        Optional<PaymentRecord> existingPayment = repository.findByReference(request.getIdempotencyKey());

        if (existingPayment.isPresent()) {

            return existingPayment.get();
        }
        Long paymentId;
        Random random = new Random();
        do {
            paymentId = (long) (random.nextInt(9999) + 1);
        } while (repository.existsById(paymentId));
        PaymentRecord payment = new PaymentRecord();
        payment.setPaymentId(paymentId);
        payment.setOrderId(request.getOrderId());
        payment.setAmount(BigDecimal.valueOf(request.getAmount()));
        payment.setMethod(request.getMethod());
        payment.setStatus("SUCCESS");
        payment.setReference(request.getIdempotencyKey() != null && !request.getIdempotencyKey().isEmpty() ? request.getIdempotencyKey() : UUID.randomUUID().toString());
        payment.setCreatedAt(LocalDateTime.now());

        return repository.save(payment);
    }

    @Override
    public boolean refundPayment(Long orderId) throws BaseException {
        try {
            Optional<PaymentRecord> paymentOpt = repository.findByOrderId(orderId);

            if (paymentOpt.isEmpty()) {
                return false;
            }

            PaymentRecord payment = paymentOpt.get();
            payment.setStatus("REFUNDED");
            repository.save(payment);

            return true;

        } catch (IncorrectResultSizeDataAccessException ex) {
           throw new BaseException("400","Multiple orderIds found");
        }
    }
}

