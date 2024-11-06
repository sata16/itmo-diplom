package com.example.diplom.model.db.repository;

import com.example.diplom.model.db.entity.Counter;
import com.example.diplom.model.db.entity.Services;
import com.example.diplom.model.enums.AttributeCounter;
import com.example.diplom.model.enums.CounterStatus;
import com.example.diplom.model.enums.ServiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CounterRepository extends JpaRepository<Counter,Long> {
    //искл дублирование серийных номеров
    Optional<Counter> findBySerialNumberIgnoreCase(String serialNumber);

    //Получить список приборов учета по адресу
    @Query("select  c from Counter c where c.address.id = :id and c.status <> :status and c.attributeCounter = :atttibuteCounter")
    List<Counter> findCounterByAddress(@Param("id") Long id, CounterStatus status, @Param("atttibuteCounter") AttributeCounter attributeCounter);


}
