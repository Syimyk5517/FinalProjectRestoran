package com.example.finalprojectrestoran.dto.responses;

import com.example.finalprojectrestoran.entity.enums.Role;

import java.time.LocalDate;

public record UserApplicationResponse (
        Long id,
        String fullName,
        LocalDate localDate,
        Role role,
        Byte experience,
        String phoneNumber
){

}
