package com.defense.inventory.repository;

import com.defense.inventory.entity.Product;
import com.defense.inventory.entity.SubProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubProductRepository extends JpaRepository<SubProduct, Long> {

    List<SubProduct> findByNameContainingIgnoreCase(String name);
    boolean existsByBarcode(String barcode);
}
