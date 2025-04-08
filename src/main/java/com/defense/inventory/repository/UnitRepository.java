package com.defense.inventory.repository;

import com.defense.inventory.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    Unit findByName(String name);
}
