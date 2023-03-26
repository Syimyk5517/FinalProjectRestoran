package com.example.finalprojectrestoran.service.serviceImpl;

import com.example.finalprojectrestoran.dto.requests.CategoryRequest;
import com.example.finalprojectrestoran.dto.responses.CategoryResponse;
import com.example.finalprojectrestoran.dto.responses.SimpleResponse;
import com.example.finalprojectrestoran.dto.responses.paginationResponse.CategoryPaginationResponse;
import com.example.finalprojectrestoran.entity.Category;
import com.example.finalprojectrestoran.exception.NotFoundException;
import com.example.finalprojectrestoran.repository.CategoryRepository;
import com.example.finalprojectrestoran.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAllCategories();
    }

    @Override
    public SimpleResponse saveCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.name());
        categoryRepository.save(category);
        return SimpleResponse.builder().status(HttpStatus.OK).
                message(String.format("Category with name : %s " +
                                "successfully saved",
                        categoryRequest.name())).build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Category with id : " + id + "is not found");
        }
        categoryRepository.deleteById(id);
        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("Category with id : %s is deleted!", id)).build();

    }

    @Override
    public SimpleResponse update(Long id, CategoryRequest categoryRequest) {
       Category category = categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Category with id " + id + "is not found!"));
        category.setName(categoryRequest.name());
        categoryRepository.save(category);
        return SimpleResponse.builder().status(HttpStatus.OK).
                message(String.format("Category with name : %s " +
                                "successfully update",
                        categoryRequest.name())).build();
    }

    @Override
    public CategoryResponse findById(Long id) {
        return categoryRepository.getCategoriesById(id).orElseThrow(
                () -> new NotFoundException("Category with id : " + id + "is not found!"));
    }

    @Override
    public CategoryPaginationResponse getCategoryPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size, Sort.by("name"));
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        CategoryPaginationResponse categoryPaginationResponse = new CategoryPaginationResponse();
        categoryPaginationResponse.setCategoryResponses(convert(categoryPage.getContent()));
        categoryPaginationResponse.setCurrentPage(pageable.getPageNumber());
        categoryPaginationResponse.setCurrentPageSize(categoryPage.getTotalPages());
        return categoryPaginationResponse;


    }
    private CategoryResponse convert(Category category){
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName()).build();
    }
    private List<CategoryResponse> convert(List<Category> categories){
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        for (Category c :categories) {
            categoryResponses.add(convert(c));
        }
        return categoryResponses;
    }

}
