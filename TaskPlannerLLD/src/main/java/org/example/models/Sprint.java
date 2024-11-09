package org.example.models;

import org.example.exceptions.TaskPlannerException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Sprint {

    private long id;
    private List<Long> tasks = new ArrayList<>();

    public Sprint(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public List<Long> getTasks() {
        return this.tasks;
    }

    public void addTask(long taskId) {
        this.tasks.add(taskId);
    }

    public void removeTask(long taskId) {
        boolean taskExisits = tasks.stream().anyMatch(task -> task==taskId);
        if(!taskExisits) {
            throw new TaskPlannerException("No task found for TaskId: "+taskId);
        }
        this.tasks = tasks.stream().filter(task -> task != taskId).collect(Collectors.toList());
    }

    public long getTaskById(long taskId) {

        long foundTask = tasks.stream().filter(task -> task==taskId).findFirst()
                .orElseThrow(() -> new TaskPlannerException("Sprint does not contains taskId: "+taskId));
        return foundTask;
    }
}
