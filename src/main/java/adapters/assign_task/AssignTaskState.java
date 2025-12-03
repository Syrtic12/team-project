package adapters.assign_task;

/**
 * Represents the state of the "Assign Task" use case for the view model.
 * Contains information about the success or failure of a task assignment operation.
 */
public class AssignTaskState {
    private String error = "";
    private String success = "";

    public AssignTaskState() {
    }

    public AssignTaskState(AssignTaskState copy) {
        this.error = copy.error;
        this.success = copy.success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
