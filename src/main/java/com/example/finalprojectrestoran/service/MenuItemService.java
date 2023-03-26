package com.example.finalprojectrestoran.service;

import com.example.finalprojectrestoran.dto.requests.MenuItemRequest;
import com.example.finalprojectrestoran.dto.responses.paginationResponse.MenuItemPaginationResponse;
import com.example.finalprojectrestoran.dto.responses.MenuItemResponse;
import com.example.finalprojectrestoran.dto.responses.MenuItemResponseSearch;
import com.example.finalprojectrestoran.dto.responses.SimpleResponse;
import com.example.finalprojectrestoran.entity.MenuItem;

import java.util.List;

public interface MenuItemService {
    List<MenuItemResponse> findAll();

    SimpleResponse save(MenuItemRequest menuItemRequest);

    MenuItemResponse findById(Long id);

    SimpleResponse deleteById(Long id);

    SimpleResponse updateById(Long id, MenuItem menuItem);

    List<MenuItemResponse> findAllMenuItemSortedByPriceAscAndDesc(String sort);
    List<MenuItemResponseSearch> search(String keyword);

    MenuItemPaginationResponse getMenuItemPagination(int page, int size);
    List<MenuItem> filterInIsVegetarian();
}
