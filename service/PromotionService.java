package org.example.ex4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ex4.dto.ApplyPromotionRequest;
import org.example.ex4.dto.PromotionRequest;
import org.example.ex4.entity.Order;
import org.example.ex4.entity.Promotion;
import org.example.ex4.exception.BusinessException;
import org.example.ex4.repository.OrderRepository;
import org.example.ex4.repository.PromotionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final OrderRepository orderRepository;

    public Promotion createPromotion(
            PromotionRequest request
    ) {

        Promotion promotion = Promotion.builder()
                .code(request.getCode())
                .discountPercent(request.getDiscountPercent())
                .isActive(request.getIsActive())
                .build();

        return promotionRepository.save(promotion);
    }

    public Order applyPromotion(
            ApplyPromotionRequest request
    ) {

        // Simulated Business Error
        if ("EXPIRED".equals(request.getPromoCode())) {

            throw new BusinessException(
                    "Mã giảm giá đã hết hạn"
            );
        }

        // Simulated System Error
        if ("CRASH".equals(request.getPromoCode())) {

            throw new NullPointerException(
                    "Simulated system crash"
            );
        }

        Promotion promotion = promotionRepository
                .findByCode(request.getPromoCode())
                .orElseThrow(() ->
                        new BusinessException(
                                "Mã giảm giá không tồn tại"
                        ));

        if (!promotion.getIsActive()) {

            throw new BusinessException(
                    "Mã giảm giá đã bị vô hiệu hóa"
            );
        }

        Order order = (Order) orderRepository.findById(
                        request.getOrderId()
                )
                .orElseThrow(() ->
                        new BusinessException(
                                "Không tìm thấy order"
                        ));

        double discountAmount =
                order.getTotalAmount()
                        * promotion.getDiscountPercent()
                        / 100;

        double finalAmount =
                order.getTotalAmount()
                        - discountAmount;

        order.setDiscountAmount(discountAmount);

        order.setFinalAmount(finalAmount);

        log.info(
                "Apply promotion {} success",
                request.getPromoCode()
        );

        return orderRepository.save(order);
    }
}