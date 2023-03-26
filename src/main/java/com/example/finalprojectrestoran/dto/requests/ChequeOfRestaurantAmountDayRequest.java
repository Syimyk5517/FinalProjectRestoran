package com.example.finalprojectrestoran.dto.requests;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record ChequeOfRestaurantAmountDayRequest(
        String restaurantName,
        LocalDate date
) {
}
