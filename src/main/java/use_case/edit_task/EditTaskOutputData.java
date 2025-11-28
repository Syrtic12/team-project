package use_case.edit_task;

public class EditTaskOutputData {
    private final boolean success;
    private final String message;

    public EditTaskOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}