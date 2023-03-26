package com.example.finalprojectrestoran.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class

Subcategory {
    @Id
    @SequenceGenerator(name = "subcategory_id_gen", sequenceName = "subcategory_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "subcategory_id_gen", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @ManyToOne(cascade = {PERSIST,REFRESH,MERGE,DETACH})
     @JsonIgnore
    private Category category;
    @OneToMany(mappedBy = "subcategory",cascade = {ALL})
     @JsonIgnore
    private List<MenuItem> menuItems = new ArrayList<>();
    public  void addMenuItem(MenuItem menuItem){
        menuItems.add(menuItem);
    }
}
