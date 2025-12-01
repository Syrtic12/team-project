package usecase.delete_task;

public class DeleteTaskOutputData {
    private final boolean success;
    private final String message;

    public DeleteTaskOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}
