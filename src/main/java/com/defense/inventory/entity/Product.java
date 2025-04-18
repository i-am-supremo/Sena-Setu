package com.defense.inventory.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@Data
public class Product {

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<SubProduct> subProductList;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(length = 300)
    private String description;
    @ManyToOne
    @JoinColumn(name = "company", nullable = false)
    @JsonIgnore
    private Company company;
}
