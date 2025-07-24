package com.example.user.service;


import com.example.core.dto.command.CompensateUserCommand;
import com.example.core.dto.command.CreateUserCommand;
import com.example.core.dto.command.UpdateUserCommand;
import com.example.user.model.entity.User;

import java.util.List;


public interface UserService {

    List<User> findNotDeletedUsers();

    User createUser(CreateUserCommand command);

    User updateUser(UpdateUserCommand command);

    void deleteUser(CompensateUserCommand command);
}
