package com.defense.inventory.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sub_products")
@Getter
@Setter
@Data
public class SubProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String barcode;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product", nullable = false)
    @JsonIgnore
    private Product product;
}