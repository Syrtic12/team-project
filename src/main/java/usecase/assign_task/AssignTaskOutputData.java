package usecase.assign_task;

public class AssignTaskOutputData {
    private final String taskId;
    private final String teamMemberId;
    private final boolean alreadyAssigned;
    private final String message;

    public AssignTaskOutputData(String taskId, String teamMemberId, boolean alreadyAssigned, String message) {
        this.taskId = taskId;
        this.teamMemberId = teamMemberId;
        this.alreadyAssigned = alreadyAssigned;
        this.message = message;
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

}
