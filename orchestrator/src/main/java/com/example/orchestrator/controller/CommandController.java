package com.example.orchestrator.controller;


import com.example.core.request.UserCreateRequest;
import com.example.core.request.UserUpdateRequest;
import com.example.core.response.SagaResponse;
import com.example.orchestrator.service.OrchestratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST контроллер для управления процессом создания пользователей.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class CommandController {

    private final OrchestratorService orchestratorService;

    /**
     * Обрабатывает запрос на создание пользователя и инициирует сагу.
     *
     * @param request данные пользователя
     * @return ответ с идентификатором саги
     */
    @PostMapping
    public ResponseEntity<SagaResponse> createUser(@RequestBody UserCreateRequest request) {
        log.info("Creating user {}", request);
        UUID sagaId = orchestratorService.startCreateUserSaga(request);
        return ResponseEntity.ok(new SagaResponse(sagaId));
    }

    /**
     * Обновляет существующего пользователя
     */
    @PatchMapping("/{userId}")
    public ResponseEntity<SagaResponse> updateUser(
            @PathVariable UUID userId,
            @RequestBody UserUpdateRequest request) {
        log.info("Updating user {}", request);
        orchestratorService.startUpdateUserSaga(userId, request);
        return ResponseEntity.ok(new SagaResponse(request.getSagaId()));
    }
}