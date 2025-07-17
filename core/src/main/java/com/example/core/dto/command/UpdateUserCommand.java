package com.example.core.dto.command;

import lombok.*;

import java.util.Map;
import java.util.UUID;

/**
 * Команда обновления данных пользователя.
 * <p>
 * Отправляется в сервис пользователей для изменения данных существующего пользователя.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UpdateUserCommand {
    /** Идентификатор саги */
    private UUID sagaId;
    /** Идентификатор пользователя для обновления */
    private UUID userId;
    /** Поля для обновления и их новые значения */
    private Map<String, Object> updateFields;
}