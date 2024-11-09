package org.example.models;

import java.util.ArrayList;
import java.util.List;

public class User {

    private long id;
    private List<Long> tasks = new ArrayList<>();

    public User(long id) {
        this.id = id;
    }

    public long getId() { return this.id; }

    public List<Long> getTasks() {
        return this.tasks;
    }

    public void addTask(long taskId) {
        this.tasks.add(taskId);
    }
}

