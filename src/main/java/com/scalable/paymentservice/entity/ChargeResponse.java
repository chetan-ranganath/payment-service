package com.scalable.paymentservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class ChargeResponse {
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("paymentId")
    private Long paymentId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("transactionReference")
    private String transactionReference;

}
