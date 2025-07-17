package com.example.user.exception;

/**
 * Исключение при попытке операции с несуществующим или удалённым пользователем.
 */
public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String message) {
    super(message);
  }
}

