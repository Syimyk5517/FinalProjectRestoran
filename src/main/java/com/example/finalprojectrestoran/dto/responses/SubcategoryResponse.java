package com.example.finalprojectrestoran.dto.responses;

import lombok.Builder;

@Builder
public record SubcategoryResponse(
        Long id,
        String name
)  {
}
