package com.example.finalprojectrestoran.dto.responses.paginationResponse;

import com.example.finalprojectrestoran.dto.responses.StopListResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter @Setter
public class StopListPaginationResponse {
    private List<StopListResponse> stopListResponses;
    private int currentPage;
    private int currentPageSize;
}
