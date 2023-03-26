package com.example.finalprojectrestoran.service.serviceImpl;

import com.example.finalprojectrestoran.dto.requests.MenuItemRequest;
import com.example.finalprojectrestoran.dto.responses.*;
import com.example.finalprojectrestoran.dto.responses.paginationResponse.MenuItemPaginationResponse;
import com.example.finalprojectrestoran.entity.MenuItem;
import com.example.finalprojectrestoran.entity.Restaurant;
import com.example.finalprojectrestoran.entity.StopList;
import com.example.finalprojectrestoran.entity.Subcategory;
import com.example.finalprojectrestoran.exception.AlreadyException;
import com.example.finalprojectrestoran.exception.NotFoundException;
import com.example.finalprojectrestoran.repository.MenuItemRepository;
import com.example.finalprojectrestoran.repository.RestaurantRepository;
import com.example.finalprojectrestoran.repository.StopListRepository;
import com.example.finalprojectrestoran.repository.SubcategoryRepository;
import com.example.finalprojectrestoran.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final StopListRepository stopListRepository;

    @Override
    public List<MenuItemResponse> findAll() {
        List<MenuItem> menuItems = menuItemRepository.findAll();
        List<StopList> stopLists = stopListRepository.findByDate(LocalDate.now());
        for (StopList stop:stopLists) {
            menuItems.remove(stop.getMenuItem());
        }
        return convert(menuItems);

    }

    @Override
    public SimpleResponse save(MenuItemRequest menuItemRequest)  {
        Restaurant restaurant = restaurantRepository.findById(menuItemRequest.restaurantId()).orElseThrow();
        Subcategory subcategory = subcategoryRepository.findById(menuItemRequest.subcategoryId()).orElseThrow();
        if (menuItemRequest.price().intValue() < 0) {
            throw new AlreadyException("Cannot price 0 ");
        } else {
            MenuItem menuItem = new MenuItem();
            menuItem.setName(menuItemRequest.name());
            menuItem.setImage(menuItemRequest.image());
            menuItem.setDescription(menuItemRequest.description());
            menuItem.setPrice(menuItemRequest.price());
            menuItem.setRestaurant(restaurant);
            menuItem.setSubcategory(subcategory);
            menuItem.setIsVegetarian(menuItemRequest.isVegetarian());
            subcategory.addMenuItem(menuItem);
            restaurant.addMenuItem(menuItem);
            menuItemRepository.save(menuItem);
        }
        return SimpleResponse.builder().status(HttpStatus.OK).
                message(String.format("MenuItem with name : %s  " +
                                "successfully saved",
                        menuItemRequest.name())).build();
    }

    @Override
    public MenuItemResponse findById(Long id) {
        return menuItemRepository.getMenuItemById(id).orElseThrow(
                () -> new NotFoundException("MenuItem with id : " + id + "is not found!"));
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        if (!menuItemRepository.existsById(id)) {
            throw new NotFoundException("MenuItem with id : " + id + "is not found");
        }
        menuItemRepository.deleteById(id);
        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("MenuItem with id : %s is deleted!", id)).build();

    }

    @Override
    public SimpleResponse updateById(Long id, MenuItem menuItem) {
        MenuItem menuItem1 = menuItemRepository.findById(id).orElseThrow(
                () -> new NotFoundException("MenuItem with id : " + id + "is not found!"));
        menuItem1.setName(menuItem.getName());
        menuItem1.setDescription(menuItem.getDescription());
        menuItem1.setImage(menuItem.getImage());
        menuItem1.setPrice(menuItem.getPrice());
        menuItemRepository.save(menuItem1);
        return SimpleResponse.builder().status(HttpStatus.OK).
                message(String.format("MenuItem with name : %s " +
                                "successfully update",
                        menuItem.getName())).build();
    }

    @Override
    public List<MenuItemResponse> findAllMenuItemSortedByPriceAscAndDesc(String sort) {
        if (sort.equalsIgnoreCase("asc")){
            return menuItemRepository.getAllByOrderByPriceAsc();
        }else if (sort.equalsIgnoreCase("desc")) {
            return menuItemRepository.getAllByOrderByPriceDesc();
        }else
        return new ArrayList<>();
    }

    @Override
    public List<MenuItemResponseSearch> search(String keyword) {
        return menuItemRepository.search(keyword);
    }

    @Override
    public MenuItemPaginationResponse getMenuItemPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("price"));
        Page<MenuItem> menuItemsPage = menuItemRepository.findAll(pageable);
        MenuItemPaginationResponse menuItemPaginationResponse = new MenuItemPaginationResponse();
        menuItemPaginationResponse.setMenuItemResponses(convert(menuItemsPage.getContent()));
        menuItemPaginationResponse.setCurrentPage(pageable.getPageNumber());
        menuItemPaginationResponse.setCurrentPageSize(menuItemsPage.getTotalPages());
        return menuItemPaginationResponse;
    }

    @Override
    public List<MenuItem> filterInIsVegetarian() {
        return menuItemRepository.findAll().stream().filter(menuItem -> menuItem.getIsVegetarian().equals(true)).filter(menuItem -> menuItem.getIsVegetarian().equals(false)).toList();

    }

    private MenuItemResponse convert(MenuItem menuItem) {
        return MenuItemResponse.builder().
                id(menuItem.getId()).
                price(menuItem.getPrice())
                .image(menuItem.getImage())
                .description(menuItem.getDescription())
                .isVegetarian(menuItem.getIsVegetarian())
                .name(menuItem.getName()).build();
    }

    private List<MenuItemResponse> convert(List<MenuItem> menuItems) {
        List<MenuItemResponse> menuItemResponses = new ArrayList<>();
        for (MenuItem menuItem : menuItems) {
            menuItemResponses.add(convert(menuItem));
        }
        return menuItemResponses;
    }
}
