package com.example.core.dto.event;

import lombok.*;

import java.util.UUID;

/**
 * Событие результата обновления пользователя.
 * <p>
 * Содержит информацию об успешности операции обновления пользователя.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserUpdatedEvent {
    /** Идентификатор саги */
    private UUID sagaId;
    /** Идентификатор созданного пользователя */
    private UUID userId;
    private boolean success;
    private String errorMessage;
}
