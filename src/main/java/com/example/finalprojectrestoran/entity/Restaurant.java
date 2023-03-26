package com.example.finalprojectrestoran.entity;

import com.example.finalprojectrestoran.entity.enums.RestaurantType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

import static jakarta.persistence.CascadeType.ALL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurants")
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class Restaurant {
    @Id
    @SequenceGenerator(name = "restaurant_id_gen", sequenceName = "restaurant_id_seq", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "restaurant_id_gen")
    private Long id;
    private String name;
    private String location;
    @Enumerated(EnumType.STRING)
    private RestaurantType restType;
    private Byte numberOfEmployees;
    private int service;
    @OneToMany(mappedBy = "restaurant",cascade = {ALL})

    private List<User> users = new ArrayList<>();
    public  void addUser(User user){
        users.add(user);
    }
    @OneToMany(mappedBy = "restaurant",cascade = {ALL})

    private List<MenuItem> menuItems = new ArrayList<>();
    public void addMenuItem(MenuItem menuItem){
        menuItems.add(menuItem);
    }
}
