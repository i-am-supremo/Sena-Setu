package com.defense.inventory.repository;

import com.defense.inventory.entity.Company;
import com.defense.inventory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findByUnitId(Long unitId);
}
