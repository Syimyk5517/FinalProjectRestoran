package com.example.finalprojectrestoran.api;

import com.example.finalprojectrestoran.dto.requests.CategoryRequest;
import com.example.finalprojectrestoran.dto.responses.CategoryResponse;
import com.example.finalprojectrestoran.dto.responses.SimpleResponse;
import com.example.finalprojectrestoran.dto.responses.paginationResponse.CategoryPaginationResponse;
import com.example.finalprojectrestoran.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/category")
public class CategoryApi {
    private final CategoryService categoryService;

    @GetMapping
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<CategoryResponse> findAll() {
        return categoryService.findAll();
    }

    @PostMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse saveCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.saveCategory(categoryRequest);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public CategoryResponse findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public SimpleResponse deleteCategoryById(@PathVariable Long id) {
        return categoryService.deleteById(id);
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public SimpleResponse updateCategoryById(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        return categoryService.update(id, categoryRequest);
    }

    @GetMapping("/pagination")
    public CategoryPaginationResponse getCategoryPagination(@RequestParam int page, @RequestParam int size) {
        return categoryService.getCategoryPagination(page, size);
    }
}