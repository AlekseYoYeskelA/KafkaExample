package com.example.user.model.repository;


import com.example.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для выполнения CRUD-операций с сущностью {@link User}.
 * <p>
 * Добавлены кастомные методы для работы с логическим удалением.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Находит пользователя по ID, игнорируя логически удалённых.
     */
    Optional<User> findByIdAndDeletedFalse(UUID id);

    /**
     * Находит всех неудаленных пользователей
     */
    @Query("SELECT u FROM User u WHERE u.deleted = false")
    List<User> findNotDeletedUsers();
}
