package usecase.assign_task;

public class AssignTaskInputData {
    private final String taskIdx;
    private final String teamMemberIdx;
    public final String teamLeaderIdx;

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

    public String getTeamLeaderIdx() {return teamLeaderIdx;}
}



