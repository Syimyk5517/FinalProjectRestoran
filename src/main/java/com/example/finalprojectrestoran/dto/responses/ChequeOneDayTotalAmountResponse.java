package com.example.finalprojectrestoran.dto.responses;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChequeOneDayTotalAmountResponse {
   private String walterFullName;
   private int numberOfCheques;
   private BigDecimal totalAmount;
}
