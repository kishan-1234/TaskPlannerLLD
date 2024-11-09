package org.example.models;

import org.example.exceptions.TaskPlannerException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

public class Feature extends Task {

    private String summary;
    private FeatureImpact impact;

    public Feature(long id, String title, long creator, LocalDateTime dueDate, Map<String,Object> metadata) {

        this.validateInput(id, title, creator, dueDate, metadata);
        this.id = id;
        this.title = title;
        this.creator = creator;
        changeStatus(TaskStatus.OPEN);
        this.type = TaskType.Feature;
        this.dueDate = dueDate;
        this.metadata = metadata;
        this.summary = metadata.get("summary").toString();
        this.impact = FeatureImpact.valueOf(metadata.get("impact").toString());
    }

    @Override
    protected void validateMetadata(Map<String,Object> metadata) {
        if(!metadata.containsKey("summary"))
            throw new TaskPlannerException("Please provide summary for this feature");
        if(!metadata.containsKey("impact"))
            throw new TaskPlannerException("Please provide impact for this feature");
    }

    private void validateStatus(TaskStatus status) {

        if(!(status.equals(TaskStatus.OPEN) || status.equals(TaskStatus.IN_PROGRESS) || status.equals(TaskStatus.TESTING) ||
            status.equals(TaskStatus.DEPLOYED))) {
            throw new TaskPlannerException("Feature can only have status among OPEN,IN_PROGRESS,TESTING,DEPLOYED");
        }
    }

    @Override
    public void changeStatus(TaskStatus newStatus) {

        validateStatus(newStatus);
        TaskStatus currentStatus = this.status;
        if(newStatus.equals(currentStatus)) {
            return;
        }
        if(newStatus.equals("IN_PROGRESS")) {
            if(currentStatus.equals("OPEN")) {
                this.status = newStatus;
                return;
            }
        } else if(newStatus.equals("TESTING")) {
            if(currentStatus.equals("IN_PROGRESS")) {
                this.status = newStatus;
                return;
            }
        } else if(newStatus.equals("DEPLOYED")) {
            if(currentStatus.equals("TESTING") || currentStatus.equals("IN_PROGRESS")) {
                this.status = newStatus;
                return;
            }
        }
        throw new TaskPlannerException("Cannot change taskStatus from: "+currentStatus+" to: "+newStatus);
    }
}
