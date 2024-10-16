package com.example.diplom.model.db.entity;

import com.example.diplom.model.enums.TypeCounterStatus;
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
@Table(name = "type_counter")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypeCounter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "capacity")
    Integer capacity;

    @Column(name = "name_counter")
    String nameCounter;

    @Column(name = "unit")
    String unit;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    TypeCounterStatus status;

    @OneToMany
    List<Counter> counters;
}
