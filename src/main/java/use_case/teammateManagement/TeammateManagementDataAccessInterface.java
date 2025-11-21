package use_case.teammateManagement;

import entity.User;
import entity.Team;

import java.util.List;

public interface TeammateManagementDataAccessInterface {

    public Team getTeam(String teamId);
    public User getUser(String userId);
    public List<String> getTeamMembers(Team team);
    public User getTeamLeader(Team team);
    public boolean addUser(Team team, User user);
    public boolean removeUser(Team team, User user);
}
