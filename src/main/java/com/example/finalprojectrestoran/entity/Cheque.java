package com.example.finalprojectrestoran.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cheques")
public class Cheque {
    @Id
    @SequenceGenerator(name = "cheque_id_gen", sequenceName = "cheque_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "cheque_id_gen", strategy = GenerationType.SEQUENCE)
    private Long id;
    private BigDecimal priceAverage;
    private LocalDate createdAt;
    @ManyToOne(cascade = {PERSIST,REFRESH,DETACH,MERGE},fetch = FetchType.LAZY)

    private User user;
    @ManyToMany(mappedBy = "cheques",cascade = {PERSIST,REFRESH,DETACH,MERGE},fetch = FetchType.LAZY)

    private List<MenuItem> menuItems = new ArrayList<>();
}
