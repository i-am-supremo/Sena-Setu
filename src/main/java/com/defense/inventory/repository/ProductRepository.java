package com.defense.inventory.repository;

import com.defense.inventory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCompanyId(Long companyId);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) = LOWER(:name) AND p.company.id = :companyId")
    Product findByNameIgnoreCaseAndCompanyId(@Param("name") String name, @Param("companyId") Long companyId);
}
