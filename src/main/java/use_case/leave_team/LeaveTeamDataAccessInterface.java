package use_case.leave_team;

import entity.Team;
import entity.User;

import java.util.List;

public interface LeaveTeamDataAccessInterface {


    public Team getTeam(String teamId);
    public User getUser(String userId);
    public List<String> getTeamMembers(Team team);
    public User getTeamLeader(Team team);
}
