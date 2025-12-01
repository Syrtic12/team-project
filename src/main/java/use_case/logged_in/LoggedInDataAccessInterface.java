package use_case.logged_in;

import entity.Task;
import entity.Team;

import java.util.Map;

import java.util.List;

public interface LoggedInDataAccessInterface {

    public List<Task> getTeamTasks(String id);
    public Task getTask(String id);
    public Map<String, String> getTeamMembers(String teamId);
    public String getTeamLeaderId(String teamId);

}
