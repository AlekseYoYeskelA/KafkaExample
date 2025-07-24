package com.example.orchestrator.service.impl;


import com.example.core.command.CompensateUserCommand;
import com.example.core.command.CreateUserCommand;
import com.example.core.command.UpdateUserCommand;
import com.example.core.event.UserCreatedEvent;
import com.example.core.request.UserCreateRequest;
import com.example.core.request.UserUpdateRequest;
import com.example.core.response.UserUpdateResponse;
import com.example.orchestrator.config.kafka.KafkaTopicsConfig;
import com.example.orchestrator.model.entity.SagaLog;
import com.example.orchestrator.repository.SagaLogRepository;
import com.example.orchestrator.service.OrchestratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Реализация сервиса оркестрации
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrchestratorServiceImpl implements OrchestratorService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final SagaLogRepository sagaLogRepository;
    private final KafkaTopicsConfig topicsConfig;

    //todo генерация userid тут
    @Override
    @Transactional
    public UUID startCreateUserSaga(UserCreateRequest request) {
        UUID sagaId = UUID.randomUUID();
        log.info("Starting create user saga: {}", sagaId);

        sagaLogRepository.save(
                SagaLog.init(sagaId, "CREATE_USER_INITIATED", request.toString())
        );

        CreateUserCommand command = new CreateUserCommand(
                sagaId,
                request.getFirstName(),
                request.getLastName(),
                request.getEmail()
        );

        kafkaTemplate.send(topicsConfig.getUserCreateCommandTopic(), "CREATE_USER", command);

        return sagaId;
    }


    @Override
    @Transactional
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        log.info("Received UserCreatedEvent for saga: {}", event.getSagaId());

        sagaLogRepository.save(
                SagaLog.eventReceived(
                        event.getSagaId(),
                        "USER_CREATED",
                        "User created: " + event
                )
        );

        Map<String, Object> updateFields = Map.of(
                "lastName", event.getLastName() + "_UPDATED"
        );

        UpdateUserCommand command = new UpdateUserCommand(
                event.getSagaId(),
                event.getUserId(),
                updateFields
        );

        // Логируем инициацию обновления
        sagaLogRepository.save(
                SagaLog.commandSent(
                        event.getSagaId(),
                        "UPDATE_USER_INITIATED",
                        "Update fields: " + updateFields
                )
        );

        kafkaTemplate.send(topicsConfig.getUserUpdateCommandTopic(), "UPDATE_USER", command);
    }


    @Override
    @Transactional
    public void startUpdateUserSaga(UUID userId, UserUpdateRequest request) {
        UUID sagaId = request.getSagaId();
        log.info("Starting update user saga: {} for user: {}", sagaId, userId);

        // Проверяем, что пользователь был создан в этой саге
        List<SagaLog> creationLogs = sagaLogRepository.findBySagaIdAndEventType(
                sagaId, "USER_CREATED"
        );

        if (creationLogs.isEmpty()) {
            log.error("User creation not found for saga: {}", sagaId);
            sagaLogRepository.save(
                    SagaLog.failed(
                            sagaId,
                            "UPDATE_USER_INITIATED",
                            "User creation record not found"
                    )
            );
            return;
        }

        // Логируем инициацию обновления
        sagaLogRepository.save(
                SagaLog.commandSent(
                        sagaId,
                        "UPDATE_USER_INITIATED",
                        "Update fields: " + request.getUpdateFields()
                )
        );

        // Отправляем команду обновления
        UpdateUserCommand command = new UpdateUserCommand(
                sagaId,
                userId,
                request.getUpdateFields()
        );

        kafkaTemplate.send(topicsConfig.getUserUpdateCommandTopic(), "UPDATE_USER", command);
    }

    @Override
    @Transactional
    public void handleUserUpdateResponse(UserUpdateResponse response) {
        if (response.isSuccess()) {
            handleUpdateSuccess(response);
        } else {
            handleUpdateFailure(response);
        }
    }

    private void handleUpdateSuccess(UserUpdateResponse response) {
        log.info("Update succeeded for saga: {}", response.getSagaId());
        sagaLogRepository.save(
                SagaLog.completed(
                        response.getSagaId(),
                        "USER_UPDATE_COMPLETED",
                        "User updated successfully"
                )
        );
    }

    /**
     * Обрабатывает неудачное обновление пользователя и инициирует компенсацию.
     *
     * @param response ответ с результатом обновления
     */
    private void handleUpdateFailure(UserUpdateResponse response) {
        log.error("Update failed for saga: {}, error: {}",
                response.getSagaId(), response.getErrorMessage());

        // Сохраняем лог о начале компенсации
        sagaLogRepository.save(
                SagaLog.compensationStarted(
                        response.getSagaId(),
                        "COMPENSATION_TRIGGERED",
                        response.getErrorMessage()
                )
        );

        // Создаем и отправляем команду компенсации
        CompensateUserCommand command = new CompensateUserCommand(
                response.getSagaId(),
                response.getUserId()
        );

        kafkaTemplate.send(topicsConfig.getUserCompensateCommandTopic(), "COMPENSATE_USER", command);
    }
}