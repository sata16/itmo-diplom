package com.example.diplom.model.db.entity;

import com.example.diplom.model.enums.AddressStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "address")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "city")
    String city;

    @Column(name = "name_district")
    String nameDistrict;

    @Column(name = "code_district")
    Integer codeDistrict;

    @Column(name = "street")
    String street;

    @Column(name = "house")
    String house;

    @Column(name = "korpus")
    String korpus;

    @Column(name = "flat")
    String flat;

    @Column(name = "code_fias")
    String codeFias;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    AddressStatus status;

    @ManyToMany(mappedBy = "addresses")
    List<User> users;

    @ManyToMany
    List<Service> services;

    @OneToMany
    List<Counter> counters;


}
