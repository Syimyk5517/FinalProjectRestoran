package com.example.finalprojectrestoran.repository;

import com.example.finalprojectrestoran.dto.responses.SubcategoryResponse;
import com.example.finalprojectrestoran.entity.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    List<Subcategory> findSubcategoriesByCategoryId(Long id);
    @Query("select new com.example.finalprojectrestoran.dto.responses.SubcategoryResponse(s.id,s.name) from Subcategory s where s.id = :id" )
    Optional<SubcategoryResponse> finId(Long id);
}