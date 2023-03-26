package com.example.finalprojectrestoran.repository;

import com.example.finalprojectrestoran.entity.Cheque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChequeRepository extends JpaRepository<Cheque, Long> {
}