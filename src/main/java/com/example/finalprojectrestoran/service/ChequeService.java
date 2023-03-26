package com.example.finalprojectrestoran.service;

import com.example.finalprojectrestoran.dto.requests.*;
import com.example.finalprojectrestoran.dto.responses.ChequeOfRestaurantAmountDayResponse;
import com.example.finalprojectrestoran.dto.responses.ChequeOneDayTotalAmountResponse;
import com.example.finalprojectrestoran.dto.responses.ChequeResponse;
import com.example.finalprojectrestoran.dto.responses.SimpleResponse;
import com.example.finalprojectrestoran.dto.responses.paginationResponse.ChequePaginationResponse;

import java.util.List;

public interface ChequeService {
    List<ChequeResponse> findAllCheque();
    ChequeResponse saveCheque(ChequeRequest chequeRequest);
    ChequeResponse findById(Long id);
    SimpleResponse deleteById(Long id);
    ChequeOneDayTotalAmountResponse findAllChequesOneDayTotalAmount(ChequeOneDayTotalAmountRequest chequeOneDayTotalAmountRequest);
    ChequeOfRestaurantAmountDayResponse countRestGrantTotalForDay(ChequeOfRestaurantAmountDayRequest chequeOfREstaurantAmountDayRequest);
    SimpleResponse update(ChequeUpdateRequest chequeUpdateRequest);
    ChequePaginationResponse getPagination(int size,int page);
}
