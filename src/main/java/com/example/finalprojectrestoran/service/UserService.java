package com.example.finalprojectrestoran.service;

import com.example.finalprojectrestoran.dto.requests.AnswerRequest;
import com.example.finalprojectrestoran.dto.requests.UserApplicationRequest;
import com.example.finalprojectrestoran.dto.requests.UserRequest;
import com.example.finalprojectrestoran.dto.responses.*;
import com.example.finalprojectrestoran.dto.responses.paginationResponse.MenuItemPaginationResponse;
import com.example.finalprojectrestoran.dto.responses.paginationResponse.UserPaginationResponse;
import com.example.finalprojectrestoran.entity.User;

import java.util.List;

public interface UserService {
    UserResponses authenticate(UserRequest userRequest);

    SimpleResponse saveUser(UserRequest userRequest) ;

    List<UserResponse> findAll();
    SimpleResponse deleteUserById(Long id);
   UserResponse findById(Long id);
    SimpleResponse updateUser(Long id , UserRequest userRequest);
    SimpleResponse application(UserApplicationRequest userApplicationRequest);
    List<UserApplicationResponse> findAllUsersApplications();
    SimpleResponse answer(AnswerRequest answerRequest) ;
    UserPaginationResponse getUserPagination(int page, int size);

}
