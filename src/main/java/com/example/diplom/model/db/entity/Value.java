package com.example.diplom.model.db.entity;

import com.example.diplom.model.enums.ValueStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "value")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Value {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "period")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    LocalDate period;

    @Column(name = "value")
    Long value;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    ValueStatus status;

    @ManyToOne
    Counter counter;
}
