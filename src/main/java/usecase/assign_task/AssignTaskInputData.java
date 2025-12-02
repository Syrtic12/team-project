package usecase.assign_task;

/**
 * Data class representing the input information required to assign a user
 * to a task within a team.
 * Contains the task ID, the team member's ID, and the team leader's ID.
 */
public class AssignTaskInputData {
    private final String taskIdx;
    private final String teamMemberIdx;
    private final String teamLeaderIdx;

    public AssignTaskInputData(String taskIdx, String teamMemberIdx, String teamLeaderIdx) {
        this.taskIdx = taskIdx;
        this.teamMemberIdx = teamMemberIdx;
        this.teamLeaderIdx = teamLeaderIdx;
    }

    public String getTaskIdx() {
        return taskIdx;
    }

    public String getTeamMemberIdx() {
        return teamMemberIdx;
    }

    public String getTeamLeaderIdx() {
        return teamLeaderIdx;
    }
}

