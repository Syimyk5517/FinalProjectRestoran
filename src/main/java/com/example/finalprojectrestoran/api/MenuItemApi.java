package com.example.finalprojectrestoran.api;

import com.example.finalprojectrestoran.dto.requests.MenuItemRequest;
import com.example.finalprojectrestoran.dto.responses.paginationResponse.MenuItemPaginationResponse;
import com.example.finalprojectrestoran.dto.responses.MenuItemResponse;
import com.example.finalprojectrestoran.dto.responses.MenuItemResponseSearch;
import com.example.finalprojectrestoran.dto.responses.SimpleResponse;
import com.example.finalprojectrestoran.entity.MenuItem;
import com.example.finalprojectrestoran.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/menuitem")
public class MenuItemApi {
    private final MenuItemService menuItemService;
    @GetMapping
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<MenuItemResponse> findAll(){
        return menuItemService.findAll();
    }
    @PostMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse save(@RequestBody MenuItemRequest menuItemRequest) {
        return menuItemService.save(menuItemRequest);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public  MenuItemResponse findById( @PathVariable Long id){
        return menuItemService.findById(id);
    }
    @DeleteMapping("/{menuItemId}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse deleteMenuItemById(@PathVariable Long menuItemId){
        return menuItemService.deleteById(menuItemId);
    }
    @PutMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public  SimpleResponse updateById(@PathVariable Long id , @RequestBody MenuItem menuItem){
        return menuItemService.updateById(id,menuItem);
    }
    @GetMapping("/sort")
//    @PreAuthorize("hasAnyRole('ADMIN')")
   public List<MenuItemResponse> findAllMenuItemSortedByPriceAscAndDesc(@RequestParam String sort){
        return menuItemService.findAllMenuItemSortedByPriceAscAndDesc(sort);
    }
    @GetMapping("/search")
    public List<MenuItemResponseSearch>search(@RequestParam String keyWord){
        return menuItemService.search(keyWord);
    }
   @GetMapping("/pagination")
    public MenuItemPaginationResponse getMenuItemPagination(@RequestParam int page ,@RequestParam int size){
        return menuItemService.getMenuItemPagination(page,size);
   }
   @GetMapping("/filter")
    List<MenuItem> filterInIsVegetarian(){
        return menuItemService.filterInIsVegetarian();
   }
}
