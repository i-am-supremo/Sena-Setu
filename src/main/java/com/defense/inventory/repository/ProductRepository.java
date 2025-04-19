package com.defense.inventory.repository;

import com.defense.inventory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCompanyId(Long companyId);

    boolean existsByNameAndCompanyId(String name, Long companyId);
}
