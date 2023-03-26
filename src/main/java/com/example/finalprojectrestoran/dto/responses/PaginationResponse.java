package com.example.finalprojectrestoran.dto.responses;

import com.example.finalprojectrestoran.entity.enums.RestaurantType;

public record PaginationResponse(
        String name,
        String location,
        RestaurantType restType,
        Byte numberOfEmployees,
        int service,
        int currentPage,
        int currenSize
) {

}
