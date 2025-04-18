package com.defense.inventory.repository;

import com.defense.inventory.entity.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface LoggerRepository extends JpaRepository<Logger, Long> {
    Page<Logger> findAllByOrderByUpdatedAtDesc(Pageable pageable);
}
