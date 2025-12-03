package adapters.edit_task;

/**
 * The state for editing a task.
 */
public class EditTaskState {

    private String taskId;
    private String description;
    private String title;
    private String error;
    private Integer status;
    private String teamId;

    public String getTaskId() {
        return this.taskId;
    }

    public String getDescription() {
        return this.description;

    }

    public String getTitle() {
        return this.title;

    }

    public void setTitle(String text) {
        this.title = text;
    }

    public void setDescription(String text) {
        this.description = text;
    }

    public void setTaskId(String text) {
        this.taskId = text;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTeamID() {
        return this.teamId;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setTaskID(String taskIdx) {
        this.taskId = taskIdx;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setTeamID(String teamIdx) {
        this.teamId = teamIdx;
    }
}
