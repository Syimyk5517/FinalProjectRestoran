package com.example.finalprojectrestoran.dto.responses.paginationResponse;

import com.example.finalprojectrestoran.dto.responses.MenuItemResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class MenuItemPaginationResponse {
    private List<MenuItemResponse> menuItemResponses;
    private int currentPage;
    private int currentPageSize;
}
