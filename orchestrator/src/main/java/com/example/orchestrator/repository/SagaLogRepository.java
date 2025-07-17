package com.example.orchestrator.repository;

import com.example.orchestrator.model.entity.SagaLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий для работы с логами саг в базе данных.
 */
@Repository
public interface SagaLogRepository extends JpaRepository<SagaLog, Long> {
    /**
     * Находит лог создания пользователя для саги
     *
     * @param sagaId идентификатор саги
     * @param eventType тип события
     * @return список логов, отфильтрованный по идентификатору саги и типу события
     */
    List<SagaLog> findBySagaIdAndEventType(UUID sagaId, String eventType);

    /**
     * Находит все логи для указанной саги, отсортированные по времени создания.
     *
     * @param sagaId идентификатор саги
     * @return список логов, отсортированных по возрастанию времени
     */
    List<SagaLog> findBySagaIdOrderByTimestampAsc(UUID sagaId);
}