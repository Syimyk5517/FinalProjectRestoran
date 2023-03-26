package com.example.finalprojectrestoran.dto.responses;

import com.example.finalprojectrestoran.entity.enums.RestaurantType;
import lombok.Builder;

@Builder
public record RestaurantResponse(
        Long id,
        String name,
        String location,
        RestaurantType restType,
        Byte numberOfEmployees,
        int service

) {
}
