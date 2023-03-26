package com.example.finalprojectrestoran.dto.responses;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class OneDayTotalAmountRestaurantResponse {
    int numberOfWalter;
    int numberOfCheque;
    BigDecimal grandTotal;
}
