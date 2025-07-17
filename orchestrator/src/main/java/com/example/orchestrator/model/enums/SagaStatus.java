package com.example.orchestrator.model.enums;

/**
 * Перечисление статусов выполнения этапов саги.
 */
public enum SagaStatus {
    /** Сага инициирована */
    STARTED,

    /** Событие получено оркестратором */
    EVENT_RECEIVED,

    /** Команда отправлена сервису */
    COMMAND_SENT,

    /** Запущен процесс компенсации */
    COMPENSATION_STARTED,

    /** Сага успешно завершена */
    COMPLETED,

    /** Сага завершилась с ошибкой */
    FAILED;

    /**
     * Возвращает строковое представление статуса в формате "Capitalized".
     *
     * @return Форматированное имя статуса
     */
    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
