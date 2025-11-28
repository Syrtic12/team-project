
package use_case.edit_task;

public class EditTaskInputData {
    private final String teamId;
    private final String invokedBy;
    private final String taskIdx;
    private final String newTitle;
    private final String newDescription;
    private final Integer newStatus;

    public EditTaskInputData(String teamId, String invokedBy, String taskIdx,
                             String newTitle, String newDescription, Integer newStatus) {
        this.teamId = teamId;
        this.invokedBy = invokedBy;
        this.taskIdx = taskIdx;
        this.newTitle = newTitle;
        this.newDescription = newDescription;
        this.newStatus = newStatus;
    }

    public String getTeamId() { return teamId; }
    public String getInvokedBy() { return invokedBy; }
    public String getTaskIdx() { return taskIdx; }
    public String getNewTitle() { return newTitle; }
    public String getNewDescription() { return newDescription; }
    public Integer getNewStatus() { return newStatus; }
}
