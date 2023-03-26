package com.example.finalprojectrestoran.dto.responses.paginationResponse;

import com.example.finalprojectrestoran.dto.responses.MenuItemResponse;
import com.example.finalprojectrestoran.dto.responses.UserResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserPaginationResponse {
    private List<UserResponse> userResponses;
    private int currentPage;
    private int currentPageSize;
}
