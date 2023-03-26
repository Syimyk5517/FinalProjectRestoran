package com.example.finalprojectrestoran.api;

import com.example.finalprojectrestoran.dto.requests.StopListRequest;
import com.example.finalprojectrestoran.dto.responses.SimpleResponse;
import com.example.finalprojectrestoran.dto.responses.StopListResponse;
import com.example.finalprojectrestoran.dto.responses.paginationResponse.StopListPaginationResponse;
import com.example.finalprojectrestoran.service.StopListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/stopList")
public class StopListApi {
    private final StopListService service;
    @GetMapping
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<StopListResponse> findAll(){
        return service.findAllStopList();
    }
    @PostMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse saveStopList(@RequestBody StopListRequest stopListRequest) {
        return service.saveStopList(stopListRequest);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public  StopListResponse findById( @PathVariable Long id){
        return service.findById(id);
    }
    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public SimpleResponse deleteStopListById(@PathVariable Long id){
        return service.delete(id);
    }
    @PutMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public  SimpleResponse updateStopListById(@PathVariable Long id , @RequestBody StopListRequest stopListRequest){
        return service.update(id,stopListRequest);
    }
    @GetMapping
    StopListPaginationResponse getStopListPagination(@RequestParam int size,@RequestParam int page){
        return service.getPagination(size,page);
    }
}
