package use_case.create_task;

import use_case.team.TaskInfo;

import java.util.Map;

public class CreateTaskOutputData {
    private final boolean success;
    private final String taskIdx;
    private final String message;
    private final Map<String, TaskInfo> NotStartedTasks;
    private final Map<String, TaskInfo> InProgressTasks;
    private final Map<String, TaskInfo> CompletedTasks;

    public CreateTaskOutputData(boolean success, String taskIdx, String message, Map<String,
                                        TaskInfo> NotStartedTasks, Map<String, TaskInfo> InProgressTasks,
                                Map<String, TaskInfo> CompletedTasks) {
        this.success = success;
        this.taskIdx = taskIdx;
        this.message = message;
        this.NotStartedTasks = NotStartedTasks;
        this.InProgressTasks = InProgressTasks;
        this.CompletedTasks = CompletedTasks;
    }

    public boolean isSuccess() { return success; }
    public String getTaskIdx() { return taskIdx; }
    public String getMessage() { return message; }
    public Map<String, TaskInfo> getNotStartedTasks() { return NotStartedTasks; }
    public Map<String, TaskInfo> getInProgressTasks() { return InProgressTasks; }
    public Map<String, TaskInfo> getCompletedTasks() { return CompletedTasks; }
}
