package com.example.user.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность пользователя для хранения в базе данных.
 * <p>
 * Содержит основные данные пользователя: идентификатор, имя, фамилию, email и флаг удаления.
 * Реализует концепцию логического удаления через поле {@code deleted}.
 */
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    /** Уникальный идентификатор пользователя */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** Имя пользователя */
    @Column(nullable = false)
    private String firstName;

    /** Фамилия пользователя */
    @Column(nullable = true)
    private String lastName;

    /** Email пользователя */
    @Column(nullable = true, unique = true)
    private String email;

    /** Флаг логического удаления */
    @Column(nullable = false)
    private boolean deleted;

    /** Дата создания записи */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /** Дата последнего обновления */
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}