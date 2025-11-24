package use_case.create_task;

public class CreateTaskOutputData {
    private final boolean success;
    private final String taskIdx;
    private final String message;

    public CreateTaskOutputData(boolean success, String taskIdx, String message) {
        this.success = success;
        this.taskIdx = taskIdx;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getTaskIdx() { return taskIdx; }
    public String getMessage() { return message; }
}
