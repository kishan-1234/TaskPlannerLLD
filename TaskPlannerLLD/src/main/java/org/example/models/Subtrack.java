package org.example.models;

import org.example.exceptions.TaskPlannerException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

public class Subtrack extends Task {

    private long storyId;

    public Subtrack(long id, String title, long creator, LocalDateTime dueDate, Map<String,Object> metadata) {

        this.validateInput(id, title, creator, dueDate, metadata);
        this.id = id;
        this.title = title;
        this.creator = creator;
        changeStatus(TaskStatus.OPEN);
        this.type = TaskType.Subtrack;
        this.dueDate = dueDate;
        this.metadata = metadata;
        this.storyId = Long.valueOf(metadata.get("storyId").toString());
    }

    protected void validateInput(long id, String title, User creator, String status, Timestamp dueDate, Map<String,Object> metadata) {

        if(id<=0) {
            throw new TaskPlannerException("TaskId cant be empty");
        }
        if(title.isEmpty()) {
            throw new TaskPlannerException("Title cant be empty");
        }
        if(status.isEmpty()) {
            throw new TaskPlannerException("Status cant be empty");
        }
        validateMetadata(metadata);
    }

    @Override
    protected void validateMetadata(Map<String,Object> metadata) {
        if(!metadata.containsKey("storyId"))
            throw new TaskPlannerException("Please provide storyId for this subtrack");
    }

    private void validateStatus(TaskStatus status) {

        if(!(status.equals(TaskStatus.OPEN) || status.equals(TaskStatus.IN_PROGRESS) || status.equals(TaskStatus.COMPLETED))) {
            throw new TaskPlannerException("Subtrack can only have status among OPEN,IN_PROGRESS,COMPLETED");
        }
    }
    @Override
    public void changeStatus(TaskStatus newStatus) {

        validateStatus(newStatus);
        this.status = newStatus;
    }

}
