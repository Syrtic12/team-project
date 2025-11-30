
package use_case.team;

import data_access.TeamDataAccessObject;
import entity.Team;
import entity.User;

import java.util.ArrayList;
import java.util.List;

public class TeamInteractor implements TeamInputBoundary{
    private final TeamDataAccessInterface teamDataAccess;
    private final TeamOutputBoundary teamPresenter;

    public TeamInteractor(TeamDataAccessInterface teamDataAccess, TeamOutputBoundary teamPresenter) {
        this.teamDataAccess = teamDataAccess;
        this.teamPresenter = teamPresenter;
    }

    @Override
    public void execute(TeamInputData teamInputData) {

    }

    @Override
    public void switchToLoggedInView() {
        teamPresenter.switchToLoggedInView();
    }

    @Override
    public void switchToManageTeamView(String teamId) {
        Team team = teamDataAccess.getTeam(teamId);
        List<String> teamMembers = teamDataAccess.getTeamMembers(team);
        List<String> teamEmails = new ArrayList<>();
        for (String userId : teamMembers){
            User user = teamDataAccess.getUser(userId);
            teamEmails.add(user.getEmail());
        }
        teamPresenter.switchToManageTeamView(teamEmails, teamId);
    }

    @Override
    public void switchToCreateTaskView() {
        teamPresenter.switchToCreateTaskView();
    }

    @Override
    public void switchToEditTaskView(String taskId, String teamId, Integer status, String title, String description) {
        teamPresenter.switchToEditTaskView(taskId, teamId, status, title, description);}
}
