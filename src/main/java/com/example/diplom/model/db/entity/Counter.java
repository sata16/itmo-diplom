package com.example.diplom.model.db.entity;

import com.example.diplom.model.enums.AttributeCounter;
import com.example.diplom.model.enums.CounterStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "counter")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Counter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "serial_number")
    String serialNumber;

    @Column(name = "data_begin")
    LocalDate dataBegin;

    @Column(name = "data_check")
    LocalDate dataCheck;

    @Column(name = "value_begin")
    Long valueBegin;

    @Column(name = "attribute_counter")
    @Enumerated(EnumType.STRING)
    AttributeCounter attributeCounter;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    CounterStatus status;

    @ManyToOne
    Address address;

    @ManyToOne
    TypeCounter typeCounter;

}
