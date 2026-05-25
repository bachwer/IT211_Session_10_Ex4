package org.example.ex4.dto;

import lombok.Data;

@Data
public class ApplyPromotionRequest {

    private Long orderId;

    private String promoCode;
}