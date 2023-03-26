package com.example.finalprojectrestoran.dto.requests;

import java.time.LocalDate;

public record ChequeOneDayTotalAmountRequest(
        Long walterId,
        LocalDate date
) {
}
