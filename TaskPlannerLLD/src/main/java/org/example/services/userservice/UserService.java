package org.example.services.userservice;

import org.example.models.User;

import java.util.Optional;

public interface UserService {
    public User createUser();
    public Optional<User> getUserById(long id);
    public void addUserTask(long userId, long taskId);
}
