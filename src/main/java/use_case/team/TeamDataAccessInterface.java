package use_case.team;
import java.util.List;

import entity.Task;
import entity.Team;
import entity.User;

public interface TeamDataAccessInterface {

    public List<String> getTeamMembers(Team team);

    public Task getTask(String taskID);

    public Team getTeam(String teamID);

    public User getUser(String userId);
}
