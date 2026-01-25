package com.antonio.napuhanci.repository;

import com.antonio.napuhanci.domain.Inflatable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InflatableRepository extends JpaRepository<Inflatable, Long> {
    List<Inflatable> findByActiveTrueOrderByNameAsc();
}
