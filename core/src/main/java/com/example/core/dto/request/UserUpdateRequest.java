package com.example.core.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Map;
import java.util.UUID;

/**
 * Запрос на обновление данных существующего пользователя.
 * <p>
 * Содержит поля, которые необходимо обновить, и их новые значения.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserUpdateRequest {
    /** Идентификатор саги */
    private UUID sagaId;
    /** Поля для обновления */
    @Size(max = 3)
    private Map<String, Object> updateFields;
}
