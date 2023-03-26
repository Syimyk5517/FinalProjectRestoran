package com.example.finalprojectrestoran.repository;

import com.example.finalprojectrestoran.dto.responses.StopListResponse;
import com.example.finalprojectrestoran.entity.StopList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface StopListRepository extends JpaRepository<StopList, Long> {
    @Query("select new com.example.finalprojectrestoran.dto.responses.StopListResponse(s.id,s.reason,s.menuItem.name,s.date) from StopList s")
    List<StopListResponse> findAllStopLists();
    @Query("select new com.example.finalprojectrestoran.dto.responses.StopListResponse(s.id,s.reason,s.menuItem.name,s.date) from StopList s where s.id=:id")
    Optional<StopListResponse>get(Long id);
    @Query("select count (*) from StopList s where s.date = :date and UPPER( s.menuItem.name) like upper(:menuItemName)")
    Integer count (LocalDate date,String menuItemName);
    List<StopList> findByDate(LocalDate date);

}