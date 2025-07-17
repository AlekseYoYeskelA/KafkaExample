package com.example.orchestrator.service.handler;

import com.example.core.event.UserCreatedEvent;
import com.example.core.response.UserUpdateResponse;
import com.example.orchestrator.service.OrchestratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Обработчик событий Kafka для сервиса оркестратора.
 * <p>
 * Использует динамические имена топиков из конфигурации KafkaTopicsConfig.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventConsumer {

    private final OrchestratorService orchestratorService;

    /**
     * Обрабатывает события создания пользователей.
     */
    @KafkaListener(
            topics = "#{kafkaTopicsConfig.userCreatedEventTopic}",
            groupId = "orchestrator-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        log.info("Received UserCreatedEvent for user: {}", event.getUserId());
        log.debug("Event details: {}", event);
        orchestratorService.handleUserCreatedEvent(event);
    }

    /**
     * Обрабатывает ответы на операции обновления.
     */
    @KafkaListener(
            topics = "#{kafkaTopicsConfig.userUpdateResponseTopic}",
            groupId = "orchestrator-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleUserUpdateResponse(UserUpdateResponse response) {
        log.info("Received UserUpdateResponse for saga: {}", response.getSagaId());
        log.debug("Response details: {}", response);
        orchestratorService.handleUserUpdateResponse(response);
    }
}