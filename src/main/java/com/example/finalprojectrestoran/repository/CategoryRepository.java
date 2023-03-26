package com.example.finalprojectrestoran.repository;

import com.example.finalprojectrestoran.dto.responses.CategoryResponse;
import com.example.finalprojectrestoran.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select new com.example.finalprojectrestoran.dto.responses.CategoryResponse(c.id,c.name) from Category c")
    List<CategoryResponse> findAllCategories();

    Optional<CategoryResponse> getCategoriesById(Long id);

    @Override
    Page<Category> findAll(Pageable pageable);

    @Override
    List<Category> findAll(Sort sort);
}