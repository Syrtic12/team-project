package interface_adapter.team;

import entity.Task;

import java.util.Map;

public class TeamState {
    private String teamID;
    private String userID;
    private String processError;
    private Map<String, String> notStartedTasks;
    private Map<String, String> inProgressTasks;
    private Map<String, String> completedTasks;

    public String getTeamName() {return teamID;}


    // the map is the id and title of the task
    public Map<String, String> getNotStartedTasks() {
        return notStartedTasks;
    }

    public Map<String, String> getInProgressTasks() {
        return inProgressTasks;
    }

    public Map<String, String> getCompletedTasks() {
        return completedTasks;
    }
}