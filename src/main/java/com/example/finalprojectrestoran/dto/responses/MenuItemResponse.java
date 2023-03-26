package com.example.finalprojectrestoran.dto.responses;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record MenuItemResponse(
        Long id,
        String name,
        String image,
        String description,
        Boolean isVegetarian,
        BigDecimal price


) {
}
