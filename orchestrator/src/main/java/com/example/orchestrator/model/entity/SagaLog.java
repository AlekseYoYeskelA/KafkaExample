package com.example.orchestrator.model.entity;

import com.example.orchestrator.model.enums.SagaStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность для хранения логов этапов выполнения саги.
 */
@Entity
@Table(name = "saga_logs")
@Getter
@Setter
public class SagaLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Идентификатор саги */
    @Column(nullable = false)
    private UUID sagaId;

    /** Тип события или команды */
    @Column(nullable = false, length = 50)
    private String eventType;

    /** Статус этапа саги */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SagaStatus status;

    /** Детальная информация о событии */
    @Column(length = 500)
    private String details;

    /** Временная метка события */
    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    // === Фабричные методы для создания логов с предустановленными статусами ===

    /**
     * Создает лог для инициированной саги.
     *
     * @param sagaId идентификатор саги
     * @param eventType тип события
     * @param details детали события
     * @return объект SagaLog
     */
    public static SagaLog init(UUID sagaId, String eventType, String details) {
        SagaLog log = new SagaLog();
        log.sagaId = sagaId;
        log.eventType = eventType;
        log.status = SagaStatus.STARTED;
        log.details = details;
        return log;
    }

    /**
     * Создает лог для полученного события.
     *
     * @param sagaId идентификатор саги
     * @param eventType тип события
     * @param details детали события
     * @return объект SagaLog
     */
    public static SagaLog eventReceived(UUID sagaId, String eventType, String details) {
        SagaLog log = new SagaLog();
        log.sagaId = sagaId;
        log.eventType = eventType;
        log.status = SagaStatus.EVENT_RECEIVED;
        log.details = details;
        return log;
    }

    /**
     * Создает лог для отправленной команды.
     *
     * @param sagaId идентификатор саги
     * @param eventType тип события
     * @param details детали события
     * @return объект SagaLog
     */
    public static SagaLog commandSent(UUID sagaId, String eventType, String details) {
        SagaLog log = new SagaLog();
        log.sagaId = sagaId;
        log.eventType = eventType;
        log.status = SagaStatus.COMMAND_SENT;
        log.details = details;
        return log;
    }

    /**
     * Создает лог для запущенной компенсации.
     *
     * @param sagaId идентификатор саги
     * @param eventType тип события
     * @param details детали события
     * @return объект SagaLog
     */
    public static SagaLog compensationStarted(UUID sagaId, String eventType, String details) {
        SagaLog log = new SagaLog();
        log.sagaId = sagaId;
        log.eventType = eventType;
        log.status = SagaStatus.COMPENSATION_STARTED;
        log.details = details;
        return log;
    }

    /**
     * Создает лог для успешно завершенной саги.
     *
     * @param sagaId идентификатор саги
     * @param eventType тип события
     * @param details детали события
     * @return объект SagaLog
     */
    public static SagaLog completed(UUID sagaId, String eventType, String details) {
        SagaLog log = new SagaLog();
        log.sagaId = sagaId;
        log.eventType = eventType;
        log.status = SagaStatus.COMPLETED;
        log.details = details;
        return log;
    }

    /**
     * Создает лог для саги, завершившейся с ошибкой.
     *
     * @param sagaId идентификатор саги
     * @param eventType тип события
     * @param errorDetails детали ошибки
     * @return объект SagaLog
     */
    public static SagaLog failed(UUID sagaId, String eventType, String errorDetails) {
        SagaLog log = new SagaLog();
        log.sagaId = sagaId;
        log.eventType = eventType;
        log.status = SagaStatus.FAILED;
        log.details = errorDetails;
        return log;
    }
}