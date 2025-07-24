package com.example.core.dto.command;

import lombok.*;

import java.util.UUID;

/**
 * Команда создания пользователя.
 * <p>
 * Отправляется в сервис пользователей для инициирования создания пользователя.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CreateUserCommand {
    /** Идентификатор саги */
    private UUID sagaId;
    /** Идентификатор пользователя */
    private UUID userId;
    /** Имя пользователя */
    private String firstName;
    /** Фамилия пользователя */
    private String lastName;
    /** Email пользователя */
    private String email;
}