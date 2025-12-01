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
    private final String leaderId;
    private final Map<String, String> teamMembers;

    public LoggedInOutputData(Map<String, TaskInfo> NotStartedTasks, Map<String, TaskInfo> InProgressTasks,
                              Map<String, TaskInfo> CompletedTasks, String teamId, String userId,  String leaderId, Map<String, String> teamMembers) {
        this.NotStartedTasks = NotStartedTasks;
        this.InProgressTasks = InProgressTasks;
        this.CompletedTasks = CompletedTasks;
        this.teamId = teamId;
        this.userId = userId;
        this.leaderId = leaderId;
        this.teamMembers = teamMembers;
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

    public String getLeaderId() {return leaderId;}

    public Map<String, String> getTeamMembers() {return teamMembers;}

}
