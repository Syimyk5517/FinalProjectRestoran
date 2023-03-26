package com.example.finalprojectrestoran.repository;

import com.example.finalprojectrestoran.dto.responses.UserApplicationResponse;
import com.example.finalprojectrestoran.dto.responses.UserResponse;
import com.example.finalprojectrestoran.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Query("select  new  com.example.finalprojectrestoran.dto.responses.UserResponse(u.id,concat(u.firstName,' ', u.LastName),u.phoneNumber,u.experience,u.dateOfBirth ) from User u")
    List<UserResponse> findAllUsers();
    boolean existsByEmail(String email);
    @Query("select  new com.example.finalprojectrestoran.dto.responses.UserResponse(u.id,concat(u.firstName,' ', u.LastName),u.phoneNumber,u.experience,u.dateOfBirth ) from User u where u.id =:id")
    Optional<UserResponse> getUser(Long id);
    @Query("select new com.example.finalprojectrestoran.dto.responses.UserApplicationResponse(u.id,concat(u.firstName,' ', u.LastName),u.dateOfBirth,u.role,u.experience,u.phoneNumber) from User u where u.restaurant.id = null ")
    List<UserApplicationResponse> findAllByUserResume();
    @Query("select User from User u where u.restaurant.id =null ")
    List<User> users();
    @Override
    Page<User> findAll(Pageable pageable);

    @Override
    List<User> findAll(Sort sort);

}