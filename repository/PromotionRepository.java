package org.example.ex4.repository;

import org.example.ex4.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromotionRepository
        extends JpaRepository<Promotion, Long> {

    Optional<Promotion> findByCode(String code);
}