package com.example.finalprojectrestoran.service;

import com.example.finalprojectrestoran.dto.requests.RestaurantRequest;
import com.example.finalprojectrestoran.dto.responses.PaginationResponse;
import com.example.finalprojectrestoran.dto.responses.RestaurantResponse;
import com.example.finalprojectrestoran.dto.responses.SimpleResponse;

import java.util.List;

public interface RestaurantService {
    SimpleResponse saveRestaurant(RestaurantRequest request);
    List<RestaurantResponse> findAll();
    RestaurantResponse findById(Long id);
    SimpleResponse delete(Long id);
    RestaurantResponse update(Long id,RestaurantRequest request);
    PaginationResponse getRestaurantResponse(int page, int size);
}
