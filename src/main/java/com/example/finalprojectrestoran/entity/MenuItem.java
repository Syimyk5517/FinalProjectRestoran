package com.example.finalprojectrestoran.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@EqualsAndHashCode
@Table(name = "menu_items")
public class MenuItem {
    @Id
    @SequenceGenerator(name = "menu_item_id_gen", sequenceName = "menu_item_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "menu_item_id_gen", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String image;
    private BigDecimal price;
    private String description;
    private Boolean isVegetarian;
    @ManyToOne(cascade = {PERSIST,REFRESH,MERGE,DETACH},fetch = FetchType.LAZY)
    @JsonIgnore
    private Restaurant restaurant;
    @OneToOne(mappedBy = "menuItem",cascade = {ALL})
    @JsonIgnore
    private StopList stopList;
    @ManyToMany(cascade = {PERSIST,REFRESH,DETACH,MERGE},fetch =FetchType.LAZY)
    @JsonIgnore
    private List<Cheque> cheques = new ArrayList<>();
    public void addCheques(Cheque cheque){
       this.cheques.add(cheque);
    }
    @ManyToOne(cascade = {DETACH,REFRESH,PERSIST,MERGE},fetch = FetchType.LAZY)
    @JsonIgnore
    private Subcategory subcategory;

}
