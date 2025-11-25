
package use_case.logged_in;

import entity.Task;
import interface_adapter.logged_in.LoggedInPresenter;

import java.util.List;
import java.util.Map;

public class LoggedInOutputData {
    private final Map<String, String> NotStartedTasks;
    private final Map<String, String> InProgressTasks;
    private final Map<String, String> CompletedTasks;
    private final String teamId;
    public LoggedInOutputData(Map<String, String> NotStartedTasks, Map<String, String> InProgressTasks,
                              Map<String, String> CompletedTasks, String teamId) {
        this.NotStartedTasks = NotStartedTasks;
        this.InProgressTasks = InProgressTasks;
        this.CompletedTasks = CompletedTasks;
        this.teamId = teamId;
    }

    public Map<String, String> getNotStartedTasks() {
        return NotStartedTasks;
    }

    public Map<String, String> getInProgressTasks() {
        return InProgressTasks;
    }

    public Map<String, String> getCompletedTasks() {
        return CompletedTasks;
    }

    public String getTeamId() {return teamId;}

}
