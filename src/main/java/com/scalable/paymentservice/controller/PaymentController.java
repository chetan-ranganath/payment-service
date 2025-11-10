package com.scalable.paymentservice.controller;

import com.scalable.paymentservice.entity.ChargeRequest;
import com.scalable.paymentservice.entity.ChargeResponse;
import com.scalable.paymentservice.entity.PaymentRecord;
import com.scalable.paymentservice.exception.BaseException;
import com.scalable.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/charge")
    public ResponseEntity<ChargeResponse> charge(
            @RequestHeader( value = "X-Correlation-ID" ,required = true) String trackingHeader,
            @RequestBody ChargeRequest request) {
        PaymentRecord payment = paymentService.chargePayment(request);
        ChargeResponse response = ChargeResponse.builder()
                .success(true)
                .paymentId(payment.getPaymentId())
                .status(payment.getStatus())
                .message("")
                .transactionReference(payment.getReference())
                .build();
        return ResponseEntity.ok(response);
    }


    @PostMapping("/{orderId}/refund")
    public ResponseEntity<Boolean> refund(@PathVariable Long orderId,@RequestHeader( value = "X-Correlation-ID" ,required = true) String trackingHeader) throws BaseException {
        boolean success = paymentService.refundPayment(orderId);
        return ResponseEntity.ok(success);
    }
}
