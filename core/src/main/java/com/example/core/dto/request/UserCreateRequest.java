package com.example.core.dto.request;

import lombok.*;

/**
 * Запрос на создание нового пользователя.
 * <p>
 * Содержит данные, необходимые для инициализации процесса создания пользователя.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserCreateRequest {
    /** Имя пользователя */
    private String firstName;
    /** Фамилия пользователя */
    private String lastName;
    /** Email пользователя */
    private String email;
}