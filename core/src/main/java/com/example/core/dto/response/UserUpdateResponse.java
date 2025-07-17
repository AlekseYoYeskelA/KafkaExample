package com.example.core.dto.response;

import lombok.*;

import java.util.UUID;

/**
 * Ответ на операцию обновления пользователя.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserUpdateResponse {
    /** Идентификатор саги */
    private UUID sagaId;
    /** Идентификатор пользователя */
    private UUID userId;
    /** Флаг успешности операции */
    private boolean success;
    /** Сообщение об ошибке (если операция не успешна) */
    private String errorMessage;
}