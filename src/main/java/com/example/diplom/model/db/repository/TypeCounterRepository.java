package com.example.diplom.model.db.repository;

import com.example.diplom.model.db.entity.TypeCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeCounterRepository extends JpaRepository<TypeCounter, Long> {
}
