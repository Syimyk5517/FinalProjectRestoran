package com.example.finalprojectrestoran.repository;

import com.example.finalprojectrestoran.dto.responses.RestaurantResponse;
import com.example.finalprojectrestoran.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("select  new  com.example.finalprojectrestoran.dto.responses.RestaurantResponse(r.id,r.name,r.location,r.restType,r.numberOfEmployees,r.service) from Restaurant r")
    List<RestaurantResponse> findAllRestaurant();
    @Query("select  new  com.example.finalprojectrestoran.dto.responses.RestaurantResponse(r.id,r.name,r.location,r.restType,r.numberOfEmployees,r.service) from Restaurant r where r.id=:id")
    Optional<RestaurantResponse> getRestaurantById(Long id);
    Optional<Restaurant> findRestaurantByName(String name);

    @Override
    Page<Restaurant> findAll(Pageable pageable);
}