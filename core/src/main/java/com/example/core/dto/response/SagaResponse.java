package com.example.core.dto.response;

import lombok.*;

import java.util.UUID;

/**
 * Ответ API, содержащий идентификатор саги.
 * <p>
 * Используется для отслеживания статуса выполнения распределенной транзакции.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class SagaResponse {
    /** Уникальный идентификатор саги */
    private UUID sagaId;
}