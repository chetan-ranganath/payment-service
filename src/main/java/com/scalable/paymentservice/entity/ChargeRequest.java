package com.scalable.paymentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChargeRequest {

    @JsonProperty("OrderId")
    private Long orderId;

    @JsonProperty("UserId")
    private Long userId;

    @JsonProperty("Method")
    private String method;

    @JsonProperty("Amount")
    private Double amount;

    @JsonProperty("IdempotencyKey")
    private String idempotencyKey;
}
