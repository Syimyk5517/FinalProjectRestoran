package com.example.finalprojectrestoran.service;

import com.example.finalprojectrestoran.dto.requests.StopListRequest;
import com.example.finalprojectrestoran.dto.responses.SimpleResponse;
import com.example.finalprojectrestoran.dto.responses.StopListResponse;
import com.example.finalprojectrestoran.dto.responses.paginationResponse.StopListPaginationResponse;

import java.util.List;

public interface StopListService {
    List<StopListResponse> findAllStopList();
    SimpleResponse saveStopList(StopListRequest stopListRequest) ;
    StopListResponse findById(Long id);
    SimpleResponse update(Long id, StopListRequest stopListRequest);
    SimpleResponse delete(Long id);
    StopListPaginationResponse getPagination(int size, int page);
}
