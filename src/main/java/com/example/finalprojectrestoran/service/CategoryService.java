package com.example.finalprojectrestoran.service;

import com.example.finalprojectrestoran.dto.requests.CategoryRequest;
import com.example.finalprojectrestoran.dto.responses.CategoryResponse;
import com.example.finalprojectrestoran.dto.responses.SimpleResponse;
import com.example.finalprojectrestoran.dto.responses.paginationResponse.CategoryPaginationResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> findAll();
    SimpleResponse saveCategory(CategoryRequest categoryRequest);
    SimpleResponse deleteById(Long id);
    SimpleResponse update(Long id,CategoryRequest categoryRequest);
    CategoryResponse findById(Long id);
    CategoryPaginationResponse getCategoryPagination(int page, int size);
}
