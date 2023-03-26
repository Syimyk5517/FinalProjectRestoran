package com.example.finalprojectrestoran.dto.responses;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record StopListResponse (
        Long id,
        String reason,
        String menuItemName,
        LocalDate date

){
}
