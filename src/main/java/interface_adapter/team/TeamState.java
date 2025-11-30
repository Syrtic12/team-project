package interface_adapter.team;

import use_case.team.TaskInfo;

import java.util.Map;

public class TeamState {
    private String teamID;
    private Map<String, TaskInfo> notStartedTasks;
    private Map<String, TaskInfo> inProgressTasks;
    private Map<String, TaskInfo> CompletedTasks;

    // or get teamid whichever one were using idk
    public String getTeamName() {
        return teamID;
    }

    // the map is the ID and description of the task

    public void setNotStartedTasks(Map<String, TaskInfo> notStartedTasks) {
        this.notStartedTasks = notStartedTasks;
    }

    public void setInProgressTasks(Map<String, TaskInfo> inProgressTasks) {
        this.inProgressTasks = inProgressTasks;
    }

    public void setCompletedTasks(Map<String, TaskInfo> CompletedTasks) {
        this.CompletedTasks = CompletedTasks;
    }

    public Map<String, TaskInfo> getNotStartedTasks() {
        return notStartedTasks;
    }

    public Map<String, TaskInfo> getInProgressTasks() {
        return inProgressTasks;
    }

    public Map<String, TaskInfo> getCompletedTasks() {
        return CompletedTasks;
    }

}