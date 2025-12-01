package adapters.create_task;

public class CreateTaskState {

    private String title = "";
    private String description = "";
    private String error;
    private String teamId;
    public String invokedBy;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInvokedBy() {
        return this.invokedBy;
    }

    public void setInvokedBy(String invokedBy) { 
        this.invokedBy = invokedBy; 
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeamID() {
        return this.teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getError() {
        return error;
    }

    public void setError(String errorMessage) {
        this.error = errorMessage;
    }
}
