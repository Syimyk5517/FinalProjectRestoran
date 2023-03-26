package com.example.finalprojectrestoran.dto.requests;

import java.util.List;

public record ChequeUpdateRequest(
        Long chequeId,
        Long waiterId,
        List<String> oldMenuItemName,
        List<String> menuItemsItemName
) {
}
