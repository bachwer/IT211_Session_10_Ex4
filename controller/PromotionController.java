package org.example.ex4.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ex4.dto.ApplyPromotionRequest;
import org.example.ex4.dto.PromotionRequest;
import org.example.ex4.entity.Order;
import org.example.ex4.entity.Promotion;
import org.example.ex4.service.PromotionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/promo")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping
    public Promotion createPromotion(
            @Valid @RequestBody PromotionRequest request
    ) {

        return promotionService.createPromotion(request);
    }

    @PostMapping("/apply")
    public Order applyPromotion(
            @RequestBody ApplyPromotionRequest request
    ) {

        return promotionService.applyPromotion(request);
    }
}