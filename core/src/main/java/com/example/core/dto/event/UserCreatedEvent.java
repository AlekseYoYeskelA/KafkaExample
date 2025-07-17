package com.example.core.dto.event;

import lombok.*;

import java.util.UUID;

/**
 * Событие успешного создания пользователя.
 * <p>
 * Генерируется сервисом пользователей после успешного создания записи в БД.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserCreatedEvent {
    /** Идентификатор саги */
    private UUID sagaId;
    /** Идентификатор созданного пользователя */
    private UUID userId;
    /** Имя пользователя */
    private String firstName;
    /** Фамилия пользователя */
    private String lastName;
    /** Email пользователя */
    private String email;
}