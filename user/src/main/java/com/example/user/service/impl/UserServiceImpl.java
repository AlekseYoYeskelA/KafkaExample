package com.example.user.service.impl;

import com.example.core.dto.command.CompensateUserCommand;
import com.example.core.dto.command.CreateUserCommand;
import com.example.core.dto.command.UpdateUserCommand;
import com.example.user.exception.UserNotFoundException;
import com.example.user.exception.UserUpdateException;
import com.example.user.model.entity.User;
import com.example.user.model.repository.UserRepository;
import com.example.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Сервис для управления операциями с пользователями.
 * <p>
 * Реализует логическое удаление через флаг {@code deleted}.
 * При обновлении эмулирует 50% вероятность ошибки.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Random random = new Random();


    @Override
    public List<User> findNotDeletedUsers() {
        return userRepository.findNotDeletedUsers();
    }

    /**
     * Создает нового пользователя.
     *
     * @param command Команда создания пользователя
     * @return Созданный пользователь
     */
    @Override
    @Transactional
    public User createUser(CreateUserCommand command) {
        log.info("Creating user: {}", command.getEmail());

        User user = User.builder()
                .id(command.getUserId())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .email(command.getEmail())
                .deleted(false)
                .build();

        User savedUser = userRepository.save(user);
        log.debug("User created: {}", savedUser.getId());
        return savedUser;
    }

    /**
     * Обновляет данные пользователя.
     * <p>
     * В 50% случаев выбрасывает {@link UserUpdateException}.
     *
     * @param command Команда обновления пользователя
     * @return Обновленный пользователь
     * @throws UserNotFoundException если пользователь не найден или удалён
     * @throws UserUpdateException   при сбое обновления
     */


    @Override
    @Transactional
    public User updateUser(UpdateUserCommand command) {
        log.info("Updating user: {}", command.getUserId());

        User user = userRepository.findByIdAndDeletedFalse(command.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + command.getUserId()));

        // Копирование разрешённых полей
        Map<String, Object> fields = command.getUpdateFields();
        fields.forEach((key, value) -> {
            if (value != null) {
                switch (key) {
                    case "firstName" -> user.setFirstName(value.toString());
                    case "lastName" -> user.setLastName(value.toString());
                    case "email" -> user.setEmail(value.toString());
                }
            }
        });

        // Эмуляция ошибки в 50% случаев
        if (random.nextBoolean()) {
            log.error("Update failed for user: {}", command.getUserId());
            throw new UserUpdateException("Random update failure");
        }

        User updatedUser = userRepository.save(user);
        log.debug("User updated: {}", updatedUser.getId());
        return updatedUser;
    }

    /**
     * Выполняет логическое удаление пользователя.
     *
     * @param command Команда компенсации
     */
    @Override
    @Transactional
    public void deleteUser(CompensateUserCommand command) {
        log.info("Deleting user (compensation): {}", command.getUserId());

        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found for deletion: " + command.getUserId()));

        user.setDeleted(true);
        userRepository.save(user);
        log.debug("User marked as deleted: {}", command.getUserId());
    }
}