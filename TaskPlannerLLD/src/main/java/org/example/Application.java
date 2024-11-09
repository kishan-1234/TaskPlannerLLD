package org.example;

import org.example.models.*;
import org.example.services.TaskPlannerService;
import org.example.services.TaskPlannerServiceInterfaceImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {

    private static TaskPlannerService taskPlannerService = new TaskPlannerServiceInterfaceImpl();

    public static void main(String[] args) {

        System.out.println("Creating sprint");
        Sprint sprint = createSprint();
        System.out.println("Got sprintId: "+sprint.getId());

        System.out.println("Creating user");
        User user = createUser();
        System.out.println("Got userId: "+user.getId());

        Map<String, Object> bugMetadata = new HashMap<>();
        bugMetadata.put("severity", "P0");
        Task bug = createTask("BUG-1", user.getId(), LocalDateTime.parse("2024-11-30T18:30:00"), TaskType.Bug, bugMetadata);
        taskPlannerService.changeTaskAsignee(bug.getId(), user.getId());
        taskPlannerService.addSprintTask(sprint.getId(), bug.getId());

        Map<String, Object> storyMetadata = new HashMap<>();
        storyMetadata.put("summary", "some story summary");
        Task story = taskPlannerService.createTask("STORY-1", user.getId(), LocalDateTime.parse("2023-11-30T18:30:00"), TaskType.Story, storyMetadata);
        taskPlannerService.changeTaskAsignee(story.getId(), user.getId());
        taskPlannerService.addSprintTask(sprint.getId(), story.getId());

        Map<String, Object> subtrackMetadata = new HashMap<>();
        subtrackMetadata.put("storyId", story.getId());
        Task subtrack = taskPlannerService.createTask("SUBTRACK-1", user.getId(), LocalDateTime.parse("2024-11-30T18:30:00"), TaskType.Subtrack, subtrackMetadata);
        taskPlannerService.changeTaskAsignee(subtrack.getId(), user.getId());
        taskPlannerService.addSprintTask(sprint.getId(), subtrack.getId());

        Map<Task, TaskTimelineStatus> sprintSnapshot = taskPlannerService.getSprintSnapshot(sprint.getId());

        taskPlannerService.removeSprintTask(sprint.getId(), bug.getId());

        sprintSnapshot = taskPlannerService.getSprintSnapshot(sprint.getId());

        Map<TaskType, List<Task>> tasks = taskPlannerService.getUserTasksByType(user.getId());

        System.out.println("Ending program");
    }

    private static Sprint createSprint() {
        return taskPlannerService.createSprint();
    }

    private static Task createTask(String title, long userId, LocalDateTime dueDate, TaskType taskType, Map<String, Object> bugMetadata) {
        return taskPlannerService.createTask(title, userId, dueDate, taskType, bugMetadata);
    }

    private static User createUser() {
        return taskPlannerService.createUser();
    }



}