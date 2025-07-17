package com.example.user.service;


import com.example.core.command.CompensateUserCommand;
import com.example.core.command.CreateUserCommand;
import com.example.core.command.UpdateUserCommand;
import com.example.user.model.entity.User;

import java.util.List;


public interface UserService {

    public List<User> findNotDeletedUsers();

    public User createUser(CreateUserCommand command);

    public User updateUser(UpdateUserCommand command);

    public void deleteUser(CompensateUserCommand command);
}
