package com.example.finalprojectrestoran.dto.requests;

import com.example.finalprojectrestoran.entity.enums.Role;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.time.LocalDate;
@Builder
public record UserApplicationRequest(
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String email,
        String password,
        String phoneNumber,
        Role role,
        Byte experience
) {
}
