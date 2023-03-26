package com.example.finalprojectrestoran.api;

import com.example.finalprojectrestoran.dto.requests.*;
import com.example.finalprojectrestoran.dto.responses.*;
import com.example.finalprojectrestoran.dto.responses.paginationResponse.ChequePaginationResponse;
import com.example.finalprojectrestoran.service.ChequeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cheque")
public class ChequeApi {
    private final ChequeService chequeService;

    @GetMapping
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<ChequeResponse> findAll() {
        return chequeService.findAllCheque();
    }

    @PostMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ChequeResponse saveCheque(@RequestBody ChequeRequest chequeRequest) {
        return chequeService.saveCheque(chequeRequest);
    }

    @GetMapping("/{id}")
    public ChequeResponse findById(@PathVariable Long id) {
        return chequeService.findById(id);
    }

    @DeleteMapping("/{id}")

    public SimpleResponse deleteChequeById(@PathVariable Long id) {
        return chequeService.deleteById(id);
    }

    @GetMapping("/userTotal")

    public ChequeOneDayTotalAmountResponse findAllChequesOneDayTotalAmount(@RequestBody ChequeOneDayTotalAmountRequest chequeOneDayTotalAmountRequest) {
        return chequeService.findAllChequesOneDayTotalAmount(chequeOneDayTotalAmountRequest);
    }

    @PutMapping
    public ChequeOfRestaurantAmountDayResponse countRestGrantTotalForDay(@RequestBody ChequeOfRestaurantAmountDayRequest chequeOfREstaurantAmountDayRequest) {
        return chequeService.countRestGrantTotalForDay(chequeOfREstaurantAmountDayRequest);

    }

    @PutMapping("/update")
    public SimpleResponse update(@RequestBody ChequeUpdateRequest chequeUpdateRequest) {
        return chequeService.update(chequeUpdateRequest);
    }
        @GetMapping("/pagination")
    public ChequePaginationResponse getChequePagination(@RequestParam int size,@RequestParam int page){
        return chequeService.getPagination(size, page);
        }

}
