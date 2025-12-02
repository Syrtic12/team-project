package usecase.assign_task;

import entity.Task;

/**
 * Data class representing the output of the "Assign Task" use case.
 * Contains information about the assigned task, the team member,
 * whether the member was already assigned, a status message, and
 * the updated task object.
 */
public class AssignTaskOutputData {
    private final String taskId;
    private final String teamMemberId;
    private final boolean alreadyAssigned;
    private final String message;
    private final Task updatedTask;

    public AssignTaskOutputData(
            String taskId, String teamMemberId, boolean alreadyAssigned, String message, Task updatedTask) {
        this.taskId = taskId;
        this.teamMemberId = teamMemberId;
        this.alreadyAssigned = alreadyAssigned;
        this.message = message;
        this.updatedTask = updatedTask;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTeamMemberId() {
        return teamMemberId;
    }

    public boolean isAlreadyAssigned() {
        return alreadyAssigned;
    }

    public String getMessage() {
        return message;
    }

    public Task getUpdatedTask() {
        return updatedTask;
    }
}
