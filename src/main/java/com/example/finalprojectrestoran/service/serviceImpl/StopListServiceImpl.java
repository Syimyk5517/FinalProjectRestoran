package com.example.finalprojectrestoran.service.serviceImpl;

import com.example.finalprojectrestoran.dto.requests.StopListRequest;
import com.example.finalprojectrestoran.dto.responses.SimpleResponse;
import com.example.finalprojectrestoran.dto.responses.StopListResponse;
import com.example.finalprojectrestoran.dto.responses.paginationResponse.StopListPaginationResponse;
import com.example.finalprojectrestoran.dto.responses.paginationResponse.SupCategoryPaginationResponse;
import com.example.finalprojectrestoran.entity.MenuItem;
import com.example.finalprojectrestoran.entity.StopList;
import com.example.finalprojectrestoran.exception.AlreadyException;
import com.example.finalprojectrestoran.exception.NotFoundException;
import com.example.finalprojectrestoran.repository.MenuItemRepository;
import com.example.finalprojectrestoran.repository.StopListRepository;
import com.example.finalprojectrestoran.service.StopListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Service
public class StopListServiceImpl implements StopListService {
    private final MenuItemRepository menuItemRepository;
    private final StopListRepository stopListRepository;

    @Override
    public List<StopListResponse> findAllStopList() {
        return stopListRepository.findAllStopLists();
    }

    @Override
    public SimpleResponse saveStopList(StopListRequest stopListRequest)  {
        if (stopListRepository.count(stopListRequest.date(), stopListRequest.menuItemName()) > 0){
            throw new  AlreadyException("already on this date there is such a menu item");
        }
        StopList stopList = new StopList();
        stopList.setReason(stopListRequest.reason());
        stopList.setDate(stopListRequest.date());
        MenuItem menuItem = menuItemRepository.findByName(stopListRequest.menuItemName()).orElseThrow(
                () -> new NotFoundException("MenuItem with name: " + stopListRequest.menuItemName() + "is not found!"));
        menuItem.setStopList(stopList);
        stopList.setMenuItem(menuItem);
        stopListRepository.save(stopList);

        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("StopList with MenuItemName : %s " + "successfully saved", stopListRequest.menuItemName())).build();
    }
    @Override
    public StopListResponse findById(Long id) {
        return stopListRepository.get(id).orElseThrow(()->
                        new NotFoundException("StopList with id : " + id + "is not found!"));
    }

    @Override
    public SimpleResponse update(Long id, StopListRequest stopListRequest) {
        StopList stopList = stopListRepository.findById(id).orElseThrow(() -> new NotFoundException("StopList with id : " + id + "is not found!"));
        MenuItem menuItem = menuItemRepository.findByName(stopListRequest.menuItemName()).orElseThrow(
                () -> new NotFoundException("MenuItem with name: " + stopListRequest.menuItemName() + "is not found!"));
        menuItem.setStopList(stopList);
        stopList.setMenuItem(menuItem);
        stopList.setReason(stopListRequest.reason());
        stopList.setDate(stopListRequest.date());
        stopListRepository.save(stopList);
        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("StopList with MenuItemName : %s " + "successfully update", stopListRequest.menuItemName())).build();
    }

    @Override
    public SimpleResponse delete(Long id) {
        if (!stopListRepository.existsById(id)) {
            throw new NotFoundException("StopList with id : " + id + "is not found");
        }
        stopListRepository.deleteById(id);
        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("StopList with id : %s is deleted!", id)).build();
    }

    @Override
    public StopListPaginationResponse getPagination(int size, int page) {
        Pageable pageable = PageRequest.of(page-1,size, Sort.by("getDate"));
        Page<StopList> stopListPage = stopListRepository.findAll(pageable);
        StopListPaginationResponse stopListPaginationResponse = new StopListPaginationResponse();
        stopListPaginationResponse.setStopListResponses(convert(stopListPage.getContent()));
        stopListPaginationResponse.setCurrentPage(pageable.getPageNumber());
        stopListPaginationResponse.setCurrentPageSize(stopListPage.getTotalPages());
        return stopListPaginationResponse;
    }

    private StopListResponse convert(StopList stopList){
        return StopListResponse.builder()
                .id(stopList.getId())
                .menuItemName(stopList.getMenuItem().getName())
                .date(stopList.getDate())
                .reason(stopList.getReason()).build();
    }
    private List<StopListResponse> convert(List<StopList> stopLists){
        List<StopListResponse> stopListResponses = new ArrayList<>();
        for (StopList s:stopLists) {
            stopListResponses.add(convert(s));
        }
        return stopListResponses;
    }
}
