package com.defense.inventory.repository;

import com.defense.inventory.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findByUnitId(Long unitId);

    List<Company> findByNameContainingIgnoreCase(String name);

    @Query("SELECT COUNT(c) > 0 FROM Company c WHERE LOWER(c.name) = LOWER(:name) AND c.unit.id = :unitId")
    Company existsByNameIgnoreCaseAndUnitId(@Param("name") String name, @Param("unitId") Long unitId);
}
