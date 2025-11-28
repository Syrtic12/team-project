package interface_adapter.edit_task;

public class EditTaskState {
    // Note for whoever is doing this
    // need to get this information from the controllers or presenters or whatever
    private String taskId;
    private String description;
    private String title;
    private String error;

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

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
