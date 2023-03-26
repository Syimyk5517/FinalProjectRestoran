package com.example.finalprojectrestoran.dto.requests;

import java.util.List;

public record ChequeRequest (
        Long waiterId,
        List<String> menuItemName



){
}
