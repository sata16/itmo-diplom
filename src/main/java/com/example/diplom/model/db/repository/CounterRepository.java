package com.example.diplom.model.db.repository;

import com.example.diplom.model.db.entity.Counter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CounterRepository extends JpaRepository<Counter,Long> {
    //искл дублирование серийных номеров
    Optional<Counter> findBySerialNumberIgnoreCase(String serialNumber);
}
