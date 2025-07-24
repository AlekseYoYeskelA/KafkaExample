package com.example.orchestrator.service;






import com.example.core.dto.event.UserCreatedEvent;
import com.example.core.dto.request.UserCreateRequest;
import com.example.core.dto.request.UserUpdateRequest;
import com.example.core.dto.response.UserUpdateResponse;

import java.util.UUID;

/**
 * Сервис для оркестрации процессов
 */
public interface OrchestratorService {

    /**
     * Запускает сагу создания пользователя
     */
    UUID startCreateUserSaga(UserCreateRequest request);

    /**
     * Обрабатывает событие создания пользователя
     */
    void handleUserCreatedEvent(UserCreatedEvent event);

    /**
     * Запускает сагу обновления пользователя
     */
    void startUpdateUserSaga(UUID userId, UserUpdateRequest request);

    /**
     * Обрабатывает ответ на операцию обновления
     */
    void handleUserUpdateResponse(UserUpdateResponse response);
}