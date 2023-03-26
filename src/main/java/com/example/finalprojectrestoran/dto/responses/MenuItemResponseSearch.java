package com.example.finalprojectrestoran.dto.responses;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
public record MenuItemResponseSearch(
        String categoryName,
        String subCategoryName,
        String menuItemName,
        String image,
        BigDecimal price) {
}
