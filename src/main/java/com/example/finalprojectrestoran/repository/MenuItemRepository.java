package com.example.finalprojectrestoran.repository;

import com.example.finalprojectrestoran.dto.responses.MenuItemResponse;
import com.example.finalprojectrestoran.dto.responses.MenuItemResponseSearch;
import com.example.finalprojectrestoran.entity.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    @Query("SELECT new com.example.finalprojectrestoran.dto.responses.MenuItemResponseSearch(c.name,s.name,m.name,m.image,m.price) " +
            "FROM MenuItem  m join  m.subcategory s join s.category c where " +
            "(m.name ILIKE %:keyWord% OR c.name ILIKE %:keyWord% OR s.name ILIKE %:keyWord%)")
    List<MenuItemResponseSearch> search(String keyWord);
    List<MenuItemResponse> getAllByOrderByPriceAsc();
    List<MenuItemResponse> getAllByOrderByPriceDesc();
    Optional<MenuItem> findByName(String name);
    Optional<MenuItemResponse> getMenuItemById(Long id);

    @Override
    Page<MenuItem> findAll(Pageable pageable);

    @Override
    List<MenuItem> findAll(Sort sort);
}