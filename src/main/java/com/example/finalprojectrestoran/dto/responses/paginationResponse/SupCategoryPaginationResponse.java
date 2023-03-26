package com.example.finalprojectrestoran.dto.responses.paginationResponse;

import com.example.finalprojectrestoran.dto.responses.SubcategoryResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class SupCategoryPaginationResponse {
    private List<SubcategoryResponse> subcategoryResponses;
    private int currentPage;
    private int currentPageSize;
}
