package com.example.diplom.model.db.repository;
import com.example.diplom.model.db.entity.Services;
import com.example.diplom.model.enums.ServiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Services,Long> {
    //Получить список услуг по адресу
    @Query("select s from Services s join s.addresses a on a.id = :id and s.status <> :status")
    List<Services> findServicesByAddresses(@Param("id") Long id, ServiceStatus status);
}
