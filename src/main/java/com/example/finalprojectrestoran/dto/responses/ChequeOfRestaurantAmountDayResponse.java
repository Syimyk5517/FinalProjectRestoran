package com.example.finalprojectrestoran.dto.responses;

import lombok.Data;

@Data
public class   ChequeOfRestaurantAmountDayResponse {
    private int numberOfWaiters;
    private int numberOfCheque;
    private int priceAverage;
}
