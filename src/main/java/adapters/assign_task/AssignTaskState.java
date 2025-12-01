package adapters.assign_task;

public class AssignTaskState {
    private String error = null;
    private String success = null;

    public AssignTaskState() {}

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
