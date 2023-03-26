package com.example.finalprojectrestoran.dto.responses;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserResponse (
        Long id,
        String fullName,
        String phoneNumber,
        Byte  experience,
        LocalDate dateOfBirth


) {
}
