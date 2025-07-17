package com.example.user.exception;

/**
 * Исключение при неудачном обновлении пользователя.
 */
public class UserUpdateException extends RuntimeException {
    public UserUpdateException(String message) {
        super(message);
    }
}
