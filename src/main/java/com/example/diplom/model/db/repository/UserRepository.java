package com.example.diplom.model.db.repository;
import com.example.diplom.model.db.entity.User;
import com.example.diplom.model.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("select us from User us where us.status <> :status")
    Page<User> findByUserStatusNot(Pageable request, UserStatus status);
    //фильтр на атрибут пользователя (поставщик или потребитель) и статус
    @Query("select us from User us where us.status <> :status and cast(us.attributeUser AS string) like %:filter%")
    Page<User> findByUserStatusAndAttributeUserNot(Pageable request, UserStatus status, @Param("filter") String filter);

    //Получить список пользователей по адресу
    @Query("select us from User us join us.addresses a on a.id = :id and us.status <> :status")
            List<User>findUsersByAddresses(@Param("id") Long id,UserStatus status);

    //исключение дублирования email
    Optional<User> findByEmailIgnoreCase(String email);
}
