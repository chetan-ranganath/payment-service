package com.scalable.paymentservice.service;

import com.scalable.paymentservice.entity.ChargeRequest;
import com.scalable.paymentservice.entity.PaymentRecord;
import com.scalable.paymentservice.exception.BaseException;

public interface PaymentService {
    PaymentRecord chargePayment(ChargeRequest request);

    boolean refundPayment(Long orderId) throws BaseException;
}
