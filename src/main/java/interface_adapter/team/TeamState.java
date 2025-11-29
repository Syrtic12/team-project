package interface_adapter.team;

import java.util.Map;

public class TeamState {
    private String teamID;
    private Map<String, String> notStartedTasks;
    private Map<String, String> inProgressTasks;
    private Map<String, String> CompletedTasks;

    // or get teamid whichever one were using idk
    public String getTeamName() {
        return teamID;
    }

    // the map is the ID and description of the task

    public void setNotStartedTasks(Map<String, String> notStartedTasks) {
        this.notStartedTasks = notStartedTasks;
    }

    public void setInProgressTasks(Map<String, String> inProgressTasks) {
        this.inProgressTasks = inProgressTasks;
    }

    public void setCompletedTasks(Map<String, String> CompletedTasks) {
        this.CompletedTasks = CompletedTasks;
    }

    public Map<String, String> getNotStartedTasks() {
        return notStartedTasks;
    }

    public Map<String, String> getInProgressTasks() {
        return inProgressTasks;
    }

    public Map<String, String> getCompletedTasks() {
        return CompletedTasks;
    }

}