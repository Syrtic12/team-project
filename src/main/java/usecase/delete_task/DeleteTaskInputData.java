package usecase.delete_task;

public class DeleteTaskInputData {
    private final String teamId;
    private final String invokedBy;
    private final String taskIdx;

    public DeleteTaskInputData(String teamId, String invokedBy, String taskIdx) {
        this.teamId = teamId;
        this.invokedBy = invokedBy;
        this.taskIdx = taskIdx;
    }

    public String getTeamId() { return teamId; }
    public String getInvokedBy() { return invokedBy; }
    public String getTaskIdx() { return taskIdx; }
}
