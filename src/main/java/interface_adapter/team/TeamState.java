package interface_adapter.team;

import java.util.Map;

public class TeamState {
    private String teamID;
    private Map<String, String> notStartedTasks;
    private Map<String, String> inProgressTasks;
    private Map<String, String> CompletedTasks;

    // or get teamid whichever one were using idk
    public String getTeamName() {
    }

    // the map is the title and description of the task
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
