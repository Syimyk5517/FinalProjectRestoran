package com.example.finalprojectrestoran.dto.requests;

public record AnswerRequest(
        String message,
        Long userId,
        String restaurantName
)  {
}
