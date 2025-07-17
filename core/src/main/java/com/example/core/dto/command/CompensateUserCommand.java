package com.example.core.dto.command;

import lombok.*;

import java.util.UUID;

/**
 * Команда для выполнения компенсирующих действий (отмены создания пользователя).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CompensateUserCommand {
    /** Идентификатор саги */
    private UUID sagaId;
    /** Идентификатор пользователя, для которого выполняется компенсация */
    private UUID userId;
}