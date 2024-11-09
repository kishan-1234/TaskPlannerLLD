package org.example.models;

import org.example.exceptions.TaskPlannerException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Story extends Task {

    private String summary;
    private List<Long> subtracks = new ArrayList<>();

    public Story(long id, String title, long creator, LocalDateTime dueDate, Map<String,Object> metadata) {

        this.validateInput(id, title, creator, dueDate, metadata);
        this.id = id;
        this.title = title;
        this.creator = creator;
        changeStatus(TaskStatus.OPEN);
        this.type = TaskType.Story;
        this.dueDate = dueDate;
        this.metadata = metadata;
        this.summary = metadata.get("summary").toString();
    }

    @Override
    protected void validateMetadata(Map<String,Object> metadata) {
        if(!metadata.containsKey("summary"))
            throw new TaskPlannerException("Please provide summary for this Story");
    }

    private void validateStatus(TaskStatus status) {

        if(!(status.equals(TaskStatus.OPEN) || status.equals(TaskStatus.IN_PROGRESS) || status.equals(TaskStatus.COMPLETED))) {
            throw new TaskPlannerException("Story can only have status among OPEN,IN_PROGRESS,COMPLETED");
        }
    }

    @Override
    public void changeStatus(TaskStatus newStatus) {

        validateStatus(newStatus);
        this.status = newStatus;
    }
}
