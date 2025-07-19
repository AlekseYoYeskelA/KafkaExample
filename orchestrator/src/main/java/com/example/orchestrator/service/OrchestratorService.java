package com.example.orchestrator.service;


import com.example.core.event.UserCreatedEvent;
import com.example.core.request.UserCreateRequest;
import com.example.core.request.UserUpdateRequest;
import com.example.core.response.UserUpdateResponse;

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
     * Запускает сагу создания пользователя вместе с обновлением
     */
    UUID startCreateUserWithUpdateCommand(UserCreateRequest request);

    /**
     * Обрабатывает событие создания пользователя
     */
    void handleUserCreatedEvent(UserCreatedEvent event);

    /**
     * Обрабатывает событие создания пользователя вместе с обновлением
     */
    void handleUserCreatedWithUpdateEvent(UserCreatedEvent event);

    /**
     * Запускает сагу обновления пользователя
     */
    void startUpdateUserSaga(UUID userId, UserUpdateRequest request);

    /**
     * Обрабатывает ответ на операцию обновления
     */
    void handleUserUpdateResponse(UserUpdateResponse response);
}