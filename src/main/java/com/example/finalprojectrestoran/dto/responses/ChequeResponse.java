package com.example.finalprojectrestoran.dto.responses;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ChequeResponse {
    private Long id;
    private String WaiterFullName;
    private List<MenuItemResponse> menuItems;
    private BigDecimal priceAverage;
    private int service;
    private BigDecimal grandTotal;


}
