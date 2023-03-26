package com.example.finalprojectrestoran.api;

import com.example.finalprojectrestoran.dto.requests.AnswerRequest;
import com.example.finalprojectrestoran.dto.requests.UserApplicationRequest;
import com.example.finalprojectrestoran.dto.requests.UserRequest;
import com.example.finalprojectrestoran.dto.responses.SimpleResponse;
import com.example.finalprojectrestoran.dto.responses.UserApplicationResponse;
import com.example.finalprojectrestoran.dto.responses.UserResponse;
import com.example.finalprojectrestoran.dto.responses.UserResponses;
import com.example.finalprojectrestoran.dto.responses.paginationResponse.UserPaginationResponse;
import com.example.finalprojectrestoran.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;

    @PostMapping("/login")
    public UserResponses login(@RequestBody UserRequest userRequest) {
        return userService.authenticate(userRequest);
    }

    @GetMapping
    public List<UserResponse> findAll() {
        return userService.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse saveUser(@RequestBody UserRequest userRequest) {
        return userService.saveUser(userRequest);
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }
    @PutMapping("/{id}")
    public SimpleResponse updateUserById(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return userService.updateUser(id, userRequest);
    }

    @PostMapping("/application")
    public SimpleResponse application(@RequestBody UserApplicationRequest userApplicationRequest)  {
        return userService.application(userApplicationRequest);
    }

    @GetMapping("/findAll")
    public List<UserApplicationResponse> findAllUserApplications() {
        return userService.findAllUsersApplications();
    }
    @PutMapping
    public SimpleResponse answer(@RequestBody AnswerRequest answerRequest)  {
        return userService.answer(answerRequest);
    }
    @GetMapping("/pagination")
    public UserPaginationResponse getUserPagination(@RequestParam int page , @RequestParam int size) {
        return userService.getUserPagination(page, size);
    }
}
