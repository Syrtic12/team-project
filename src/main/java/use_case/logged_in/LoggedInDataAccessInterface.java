package use_case.logged_in;

import entity.Team;

import java.util.List;

public interface LoggedInDataAccessInterface {

    public List<Team> getTeams();
}
