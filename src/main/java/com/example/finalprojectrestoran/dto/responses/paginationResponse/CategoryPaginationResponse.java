package com.example.finalprojectrestoran.dto.responses.paginationResponse;

import com.example.finalprojectrestoran.dto.responses.CategoryResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CategoryPaginationResponse {
    private List<CategoryResponse> categoryResponses;
    private int currentPage;
    private int currentPageSize;
}
