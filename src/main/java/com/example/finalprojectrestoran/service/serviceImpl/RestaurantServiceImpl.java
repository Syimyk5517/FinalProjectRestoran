package com.example.finalprojectrestoran.service.serviceImpl;

import com.example.finalprojectrestoran.dto.requests.RestaurantRequest;
import com.example.finalprojectrestoran.dto.responses.PaginationResponse;
import com.example.finalprojectrestoran.dto.responses.RestaurantResponse;
import com.example.finalprojectrestoran.dto.responses.SimpleResponse;
import com.example.finalprojectrestoran.entity.Restaurant;
import com.example.finalprojectrestoran.exception.AlreadyException;
import com.example.finalprojectrestoran.exception.NotFoundException;
import com.example.finalprojectrestoran.repository.RestaurantRepository;
import com.example.finalprojectrestoran.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository repository;

    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest request) {
        if (repository.findAll().isEmpty()) {
            Restaurant restaurant = new Restaurant();
            restaurant.setName(request.name());
            restaurant.setLocation(request.location());
            restaurant.setRestType(request.restType());
            restaurant.setService(request.service());
            repository.save(restaurant);
        }else {
            throw new AlreadyException("You cannot add more than one restaurant");
        }
            return SimpleResponse.builder().status(HttpStatus.OK).message("Saved...").build();
    }

    @Override
    public List<RestaurantResponse> findAll() {
        return repository.findAllRestaurant();
    }

    @Override
    public RestaurantResponse findById(Long id) {
        return repository.getRestaurantById(id).orElseThrow(
                () -> new NotFoundException("Restaurant with id : " + id + "is not found!"));

    }

    @Override
    public SimpleResponse delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Restaurant with id : " + id + "is not found");
        }
        repository.deleteById(id);
        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("Restaurant with id : %s is deleted!", id)).build();
    }

    @Override
    public RestaurantResponse update(Long id, RestaurantRequest request) {
        Restaurant restaurant = repository.findById(id).orElseThrow(
                () -> new NotFoundException("Restaurant with id " + id + "is not found!"));
        restaurant.setName(request.name());
        restaurant.setService(request.service());
        restaurant.setLocation(request.location());
        restaurant.setRestType(request.restType());
        repository.save(restaurant);
        return repository.getRestaurantById(id).orElseThrow(
                () -> new NotFoundException("Restaurant with id : " + id + "is not found!"));
    }

    @Override
    public PaginationResponse getRestaurantResponse(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Restaurant> pagedRestaurant = repository.findAll(pageable);
        return null;
    }
}
