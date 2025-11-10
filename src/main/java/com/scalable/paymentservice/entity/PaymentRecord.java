package com.scalable.paymentservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "payments_records")
public class PaymentRecord {

    @Id
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "method", nullable = false)
    private String method;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "reference", unique = true)
    private String reference;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}