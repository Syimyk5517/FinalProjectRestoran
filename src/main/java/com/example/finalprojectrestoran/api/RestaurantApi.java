package com.example.finalprojectrestoran.api;

import com.example.finalprojectrestoran.dto.requests.RestaurantRequest;
import com.example.finalprojectrestoran.dto.responses.PaginationResponse;
import com.example.finalprojectrestoran.dto.responses.RestaurantResponse;
import com.example.finalprojectrestoran.dto.responses.SimpleResponse;
import com.example.finalprojectrestoran.service.RestaurantService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/restaurants")
public class RestaurantApi {
    private final RestaurantService restaurantService;

    public RestaurantApi(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<RestaurantResponse> findAllRestaurant(){
        return restaurantService.findAll();
    }

    @PostMapping("/save")
    public SimpleResponse saveRestaurant(@RequestBody RestaurantRequest restaurantRequest){
        return restaurantService.saveRestaurant(restaurantRequest);
    }

    @GetMapping("/{id}")
    public  RestaurantResponse findById( @PathVariable Long id){
        return restaurantService.findById(id);
    }
    @DeleteMapping("/{id}")
   // @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse deleteRestaurantById(@PathVariable Long id){
        return restaurantService.delete(id);
    }
    @PutMapping("/{id}")
    public  RestaurantResponse updateRestaurantById(@PathVariable Long id , @RequestBody RestaurantRequest restaurantRequest){
        return restaurantService.update(id, restaurantRequest);
    }
    @GetMapping("/pagination")
    public PaginationResponse getRestaurantPagination(@RequestParam int page,
                                                      @RequestParam int size){
return null;
    }


}
