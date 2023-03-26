package com.example.finalprojectrestoran.service.serviceImpl;

import com.example.finalprojectrestoran.dto.requests.SubcategoryRequest;
import com.example.finalprojectrestoran.dto.responses.CategoryResponse;
import com.example.finalprojectrestoran.dto.responses.SimpleResponse;
import com.example.finalprojectrestoran.dto.responses.SubcategoryResponse;
import com.example.finalprojectrestoran.dto.responses.paginationResponse.SupCategoryPaginationResponse;
import com.example.finalprojectrestoran.entity.Category;
import com.example.finalprojectrestoran.entity.Subcategory;
import com.example.finalprojectrestoran.exception.NotFoundException;
import com.example.finalprojectrestoran.repository.CategoryRepository;
import com.example.finalprojectrestoran.repository.SubcategoryRepository;
import com.example.finalprojectrestoran.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public List<Subcategory> findAll() {
        return subcategoryRepository.findAll();

    }
    @Override
    public SimpleResponse save(SubcategoryRequest subcategory) {
        Category category = categoryRepository.findById(subcategory.categoryId()).orElseThrow(  () -> new NotFoundException("User with id " + subcategory.categoryId() + "is not found!"));
        Subcategory subcategory1 = new Subcategory();
        subcategory1.setName(subcategory.name());
        category.addSubcategory(subcategory1);
        subcategory1.setCategory(category);
        categoryRepository.save(category);
        subcategoryRepository.save(subcategory1);
        return  SimpleResponse.builder().status(HttpStatus.OK).
                message(String.format("SubCategory with name : %s  " +
                                "successfully saved",
                        subcategory.name())).build();
    }

    @Override
    public SimpleResponse delete(Long id) {
        if (!subcategoryRepository.existsById(id)) {
            throw new NotFoundException("Subcategory with id : " + id + "is not found");
        }
        subcategoryRepository.deleteById(id);
        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("Subcategory with id : %s is deleted!", id)).build();
    }



    @Override
    public SimpleResponse update(Long id, SubcategoryRequest subcategoryRequest) {
        Subcategory oldSubcategory = subcategoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Subcategory with id : " + id + "is not found!"));
        Category category = categoryRepository.findById(subcategoryRequest.categoryId()).orElseThrow(
                () -> new NotFoundException("Category with id " + subcategoryRequest.categoryId() + "is not found!"));
        oldSubcategory.setName(subcategoryRequest.name());
        oldSubcategory.setCategory(category);
        subcategoryRepository.save(oldSubcategory);
       return SimpleResponse.builder().status(HttpStatus.OK).
                message(String.format("SubCategory with name : %s  " +
                                "successfully update",
                        subcategoryRequest.name())).build();
    }

    @Override
    public SubcategoryResponse findById(Long id) {
        return subcategoryRepository.finId(id).orElseThrow(
                () -> new NotFoundException("Subcategory with id : " + id + "is not found!"));
    }

    @Override
    public List<Subcategory> findAllCategoryById(Long id) {
       return subcategoryRepository.findSubcategoriesByCategoryId(id).stream().sorted(Comparator.comparing(Subcategory::getName)).toList();
    }

    @Override
    public Map<String, List<Subcategory>> findAllGroupByCategory() {
        return subcategoryRepository.findAll().stream().collect(Collectors.groupingBy(subcategory -> subcategory.getCategory().getName()));
    }

    @Override
    public SupCategoryPaginationResponse getSubcategoryPagination(int size, int page) {
        Pageable pageable = PageRequest.of(page-1,size, Sort.by("name"));
        Page<Subcategory> subcategoryPage = subcategoryRepository.findAll(pageable);
        SupCategoryPaginationResponse supCategoryPaginationResponse = new SupCategoryPaginationResponse();
        supCategoryPaginationResponse.setSubcategoryResponses(convert(subcategoryPage.getContent()));
        supCategoryPaginationResponse.setCurrentPage(pageable.getPageNumber());
        supCategoryPaginationResponse.setCurrentPageSize(subcategoryPage.getTotalPages());
        return supCategoryPaginationResponse;
    }
    private SubcategoryResponse convert(Subcategory subcategory){
        return SubcategoryResponse.builder()
                .id(subcategory.getId())
                .name(subcategory.getName()).build();
    }
    private List<SubcategoryResponse> convert(List<Subcategory> subcategories){
        List<SubcategoryResponse> subcategoryResponses = new ArrayList<>();
        for (Subcategory s :subcategories) {
            subcategoryResponses.add(convert(s));

        }
            return subcategoryResponses;
    }
}
