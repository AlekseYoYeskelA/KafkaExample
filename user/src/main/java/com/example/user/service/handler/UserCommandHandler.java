package com.example.user.service.handler;


import com.example.core.dto.command.CompensateUserCommand;
import com.example.core.dto.command.CreateUserCommand;
import com.example.core.dto.command.UpdateUserCommand;
import com.example.core.dto.event.UserCreatedEvent;
import com.example.core.dto.response.UserUpdateResponse;
import com.example.user.model.entity.User;
import com.example.user.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Обрабатывает входящие команды из Kafka.
 * <p>
 * Добавлено детальное логирование всех операций и обработка ошибок.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserCommandHandler {
    private final UserServiceImpl userService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "user.create.command")
    public void handleCreateUserCommand(CreateUserCommand command) {
        log.info("Received CreateUserCommand for saga: {}", command.getSagaId());
        try {
            User user = userService.createUser(command);
            sendUserCreatedEvent(command.getSagaId(), user);
            log.info("User creation processed: {}", user.getId());
        } catch (Exception e) {
            log.error("Failed to create user: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "user.update.command")
    public void handleUpdateUserCommand(UpdateUserCommand command) {
        log.info("Received UpdateUserCommand for user: {}", command.getUserId());
        try {
            User user = userService.updateUser(command);
            sendUserUpdateResponse(command.getSagaId(), user.getId(), true, null);
            log.info("User update successful: {}", command.getUserId());
        } catch (Exception e) {
            log.error("Update failed for user {}: {}", command.getUserId(), e.getMessage());
            sendUserUpdateResponse(command.getSagaId(), command.getUserId(), false, e.getMessage());
        }
    }

    @KafkaListener(topics = "user.compensate.command")
    public void handleCompensateCommand(CompensateUserCommand command) {
        log.info("Received CompensateUserCommand for user: {}", command.getUserId());
        try {
            userService.deleteUser(command);
            log.info("Compensation processed for user: {}", command.getUserId());
        } catch (Exception e) {
            log.error("Compensation failed for user {}: {}", command.getUserId(), e.getMessage());
        }
    }

    private void sendUserCreatedEvent(UUID sagaId, User user) {
        UserCreatedEvent event = new UserCreatedEvent();
        BeanUtils.copyProperties(user, event);
        event.setSagaId(sagaId);
        event.setUserId(user.getId());
        kafkaTemplate.send("user.created.event", event);
        log.debug("Sent UserCreatedEvent for user: {}", user.getId());
    }

    private void sendUserUpdateResponse(UUID sagaId, UUID userId, boolean success, String error) {
        UserUpdateResponse response = new UserUpdateResponse();
        response.setSagaId(sagaId);
        response.setUserId(userId);
        response.setSuccess(success);
        response.setErrorMessage(error);
        kafkaTemplate.send("user.update.response", response);
        log.debug("Sent UserUpdateResponse for saga: {}", sagaId);
    }
}
