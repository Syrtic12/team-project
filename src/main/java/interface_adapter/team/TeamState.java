package interface_adapter.team;

import java.util.Map;

public class TeamState {
    // or get teamid whichever one were using idk
    public String getTeamName() {
        return "";

    }

    // the map is the title and description of the task
    public Map<String, String> getNotStartedTasks() {
        return null;
    }

    public Map<String, String> getInProgressTasks() {
        return null;
    }

    public Map<String, String> getCompletedTasks() {
        return null;
    }
}
