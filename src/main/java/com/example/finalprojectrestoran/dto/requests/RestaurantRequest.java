package com.example.finalprojectrestoran.dto.requests;

import com.example.finalprojectrestoran.entity.Restaurant;
import com.example.finalprojectrestoran.entity.enums.RestaurantType;
import lombok.Builder;

@Builder
public record RestaurantRequest (
        String name,
        String location,
        RestaurantType restType,
        int service
){
}
