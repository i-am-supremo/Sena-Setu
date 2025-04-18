package com.defense.inventory.repository;

import com.defense.inventory.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    Unit findByName(String name);

    List<Unit> findByNameContainingIgnoreCase(String name);
}
