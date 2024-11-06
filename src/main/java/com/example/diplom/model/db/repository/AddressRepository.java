package com.example.diplom.model.db.repository;

import com.example.diplom.model.db.entity.Address;
import com.example.diplom.model.enums.AddressStatus;
import com.example.diplom.model.enums.CounterStatus;
import com.example.diplom.model.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    //все адреса с квартирами где установлен прибор учета
    @Query("select a from Address a JOIN Counter c ON c.address.id = a.id and a.status <> :addressStatus and c.status <> :counterStatus")
    Page<Address> findByAddressStatusAndCounters(Pageable request, AddressStatus addressStatus, CounterStatus counterStatus);
    //все адреса с квартирами, где установлен ПУ с фильтром на индивидуальный или общедомовой
    @Query("select a from Address a JOIN Counter c ON c.address.id = a.id and a.status <> :addressStatus and c.status <> :counterStatus and cast(c.attributeCounter AS string) like %:filter%")
    Page<Address> findByAddressStatusAndAttributeCounter(Pageable request, AddressStatus addressStatus, CounterStatus counterStatus,@Param("filter") String filter);


    //Адреса
    @Query("select a from Address a where a.status <> :status")
    Page<Address> findByAddressStatusNot(Pageable request, AddressStatus status);
    //Адреса  по районам - в дальнейшем для ввода показаний [думаю для доступа поставщикам]
    @Query("select a  from Address a where a.status <> :status and cast(a.codeDistrict AS string) like %:filter%")
    Page<Address> findByAddressStatusAndNameDistrict(Pageable request,AddressStatus status, @Param("filter") String filter);

    //Список адресов пользователя
    @Query("select a from Address a where a.id in (select ua.id from User us join us.addresses ua on us.id = :id and us.status <> :status)")
    List<Address> findAddressesByUser(@Param("id") Long id, UserStatus status);

}
