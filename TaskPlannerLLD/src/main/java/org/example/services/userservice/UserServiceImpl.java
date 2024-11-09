package org.example.services.userservice;

import org.example.exceptions.TaskPlannerException;
import org.example.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService{

    private static UserServiceImpl userService;

    private static long userIdCounter = 1;

    private List<User> users = new ArrayList<>();

    private UserServiceImpl() {}

    public static UserServiceImpl getInstance() {
        if(userService==null) {
            synchronized (UserServiceImpl.class) {
                if(userService==null) {
                    userService = new UserServiceImpl();
                }
            }
        }
        return userService;
    }

    @Override
    public User createUser() {
        User user = new User(userIdCounter++);
        users.add(user);
        return user;
    }

    @Override
    public Optional<User> getUserById(long id) {
        return users.stream().filter(user -> user.getId()==id).findFirst();
    }

    @Override
    public void addUserTask(long userId, long taskId) {

        User user = getUserById(userId)
                .orElseThrow(() -> new TaskPlannerException("User does not exists with userId: "+userId));
        user.addTask(taskId);
    }
}
