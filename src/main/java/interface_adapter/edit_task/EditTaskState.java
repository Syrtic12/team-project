package interface_adapter.edit_task;

public class EditTaskState {

    private String teamLeaderId;
    private String invokedBy;
    private String taskId;
    private String title;
    private String description;
    private String error;

    public EditTaskState() {}

    public String getTeamLeaderId() { return teamLeaderId; }
    public void setTeamLeaderId(String id) { this.teamLeaderId = id; }

    public String getInvokedBy() { return invokedBy; }
    public void setInvokedBy(String id) { this.invokedBy = id; }

    public String getTaskId() { return taskId; }
    public void setTaskId(String id) { this.taskId = id; }

    public String getTitle() { return title; }
    public void setTitle(String t) { this.title = t; }

    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }

    public String getError() { return error; }
    public void setError(String e) { this.error = e; }
}
