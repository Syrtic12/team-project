package use_case.team;

import data_access.TeamDataAccessObject;

public class TeamInteractor implements TeamInputBoundary{
    private final TeamDataAccessInterface teamDataAccess;
    private final TeamOutputBoundary teamPresenter;

    public TeamInteractor(TeamDataAccessInterface teamDataAccess, TeamOutputBoundary teamPresenter) {
        this.teamDataAccess = teamDataAccess;
        this.teamPresenter = teamPresenter;
    }

    @Override
    public void execute(TeamInputData teamInputData) {
        //IDK what to do here. Leave empty for now
    }

    @Override
    public void switchToLoggedInView() {
        teamPresenter.switchToLoggedInView();
    }

    @Override
    public void switchToManageTeamView() {
        teamPresenter.switchToManageTeamView();
    }

    @Override
    public void switchToCreateTaskView() {
        teamPresenter.switchToCreateTaskView();
    }
}
