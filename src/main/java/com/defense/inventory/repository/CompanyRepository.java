package com.defense.inventory.repository;

import com.defense.inventory.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findByUnitId(Long unitId);

    List<Company> findByNameContainingIgnoreCase(String name);
}
