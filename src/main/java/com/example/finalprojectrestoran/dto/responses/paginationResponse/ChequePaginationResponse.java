package com.example.finalprojectrestoran.dto.responses.paginationResponse;

import com.example.finalprojectrestoran.dto.responses.ChequeResponse;
import com.example.finalprojectrestoran.dto.responses.MenuItemResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ChequePaginationResponse {
    private List<ChequeResponse> menuItemResponses;
    private int currentPage;
    private int currentPageSize;
}
