package com.example.finalprojectrestoran.dto.requests;

import java.time.LocalDate;

public record StopListRequest(
        String menuItemName,
        String reason,
        LocalDate date
) {


}
