package com.example.finalprojectrestoran.service;

import com.example.finalprojectrestoran.dto.requests.SubcategoryRequest;
import com.example.finalprojectrestoran.dto.responses.SimpleResponse;
import com.example.finalprojectrestoran.dto.responses.SubcategoryResponse;
import com.example.finalprojectrestoran.dto.responses.paginationResponse.SupCategoryPaginationResponse;
import com.example.finalprojectrestoran.entity.Subcategory;

import java.util.List;
import java.util.Map;

public interface SubCategoryService {
    List<Subcategory> findAll();

    SimpleResponse save(SubcategoryRequest subcategory);

    SimpleResponse delete(Long id);
    SimpleResponse update(Long id, SubcategoryRequest subcategoryRequest);
    SubcategoryResponse findById(Long id);
    List<Subcategory> findAllCategoryById(Long id);
    Map<String,List<Subcategory>> findAllGroupByCategory();
    SupCategoryPaginationResponse getSubcategoryPagination(int size,int page);


}
