package com.defense.inventory.repository;

import com.defense.inventory.entity.SubProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubProductRepository extends JpaRepository<SubProduct, Long> {

    List<SubProduct> findByNameContainingIgnoreCase(String name);

    boolean existsByBarcode(String barcode);

    Optional<SubProduct> findByBarcode(String barcode);

    List<SubProduct> findByProductId(Long productId);
}
