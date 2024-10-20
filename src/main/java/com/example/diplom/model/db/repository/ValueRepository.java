package com.example.diplom.model.db.repository;

import com.example.diplom.model.db.entity.Value;
import com.example.diplom.model.enums.ValueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValueRepository extends JpaRepository<Value,Long> {
    //все показания по прибору учета
    @Query("select v from Value v where v.status <> :status and v.counter.id = :id")
    List<Value> findAllValueToCounter(@Param("id") Long id, ValueStatus status);

}
