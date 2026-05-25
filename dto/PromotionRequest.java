package org.example.ex4.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PromotionRequest {

    @NotBlank(message = "Code không được để trống")
    private String code;

    @Min(value = 1, message = "Discount phải >= 1")
    @Max(value = 100, message = "Discount phải <= 100")
    private Integer discountPercent;

    private Boolean isActive;
}