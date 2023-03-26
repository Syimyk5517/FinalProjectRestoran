package com.example.finalprojectrestoran.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stop_lists")
public class StopList {
    @Id
    @SequenceGenerator(name = "stop_list_id_gen", sequenceName = "stop_list_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "stop_list_id_gen", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String reason;
    private LocalDate date;
    @OneToOne(cascade = {PERSIST,REFRESH,DETACH,MERGE},fetch = FetchType.LAZY)

    private MenuItem menuItem;
}
