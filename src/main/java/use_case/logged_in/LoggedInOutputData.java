package use_case.logged_in;

import entity.Task;
import interface_adapter.logged_in.LoggedInPresenter;
import use_case.team.TaskInfo;

import java.util.List;
import java.util.Map;

public class LoggedInOutputData {
    private final Map<String, TaskInfo> NotStartedTasks;
    private final Map<String, TaskInfo> InProgressTasks;
    private final Map<String, TaskInfo> CompletedTasks;
    private final String teamId;
    private final String userId;

    public LoggedInOutputData(Map<String, String> NotStartedTasks, Map<String, String> InProgressTasks,
                              Map<String, String> CompletedTasks, String teamId, String userId) {
        this.NotStartedTasks = NotStartedTasks;
        this.InProgressTasks = InProgressTasks;
        this.CompletedTasks = CompletedTasks;
        this.teamId = teamId;
        this.userId = userId;
    }

    public Map<String, TaskInfo> getNotStartedTasks() {
        return NotStartedTasks;
    }

    public Map<String, TaskInfo> getInProgressTasks() {
        return InProgressTasks;
    }

    public Map<String, TaskInfo> getCompletedTasks() {
        return CompletedTasks;
    }

    public String getTeamId() {return teamId;}

    public String getUserId() {return userId;}

}
