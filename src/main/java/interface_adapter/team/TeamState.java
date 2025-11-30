package interface_adapter.team;

import java.util.Map;

public class TeamState {
    private String teamID;
    private Map<String, String> notStartedTasks;
    private Map<String, String> inProgressTasks;
    private Map<String, String> completedTasks;

    /**
     * @return the team name
     */
    public String getTeamName() {
        return teamID;
    }

    /**
     * @param teamName the team name to set
     */
    public void setTeamName(String teamName) {this.teamID = teamName;}

    /**
     * @param notStartedTasks the not started tasks to set
     */
    public void setNotStartedTasks(Map<String, String> notStartedTasks) {
        this.notStartedTasks = notStartedTasks;
    }

    /**
     * @param inProgressTasks the in progress tasks to set
     */
    public void setInProgressTasks(Map<String, String> inProgressTasks) {
        this.inProgressTasks = inProgressTasks;
    }

    /**
     * @param completedTasks the completed tasks to set
     */
    public void setCompletedTasks(Map<String, String> completedTasks) {
        this.completedTasks = completedTasks;
    }

    /**
     * @return the not started tasks
     */
    public Map<String, String> getNotStartedTasks() {
        return this.notStartedTasks;
    }

    /**
     * @return the in progress tasks
     */
    public Map<String, String> getInProgressTasks() {
        return this.inProgressTasks;
    }

    /**
     * @return the completed tasks
     */
    public Map<String, String> getCompletedTasks() {
        return this.completedTasks;
    }

}