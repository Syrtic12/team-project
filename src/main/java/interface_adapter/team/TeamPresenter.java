package interface_adapter.team;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_task.CreateTaskViewModel;
import interface_adapter.team.TeamState;
import interface_adapter.team.TeamViewModel;
import interface_adapter.manage_team.ManageTeamViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.create_task.CreateTaskViewModel;
import use_case.team.TeamOutputBoundary;
import use_case.team.TeamOutputData;
import use_case.create_task.CreateTaskOutputData;


public class TeamPresenter implements TeamOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;
    private final ManageTeamViewModel manageTeamViewModel;
    private final TeamViewModel teamViewModel;
    private final CreateTaskViewModel createTaskViewModel;

    public TeamPresenter(ViewManagerModel viewManagerModel, LoggedInViewModel loggedInViewModel,
                         ManageTeamViewModel manageTeamViewModel, TeamViewModel teamViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.manageTeamViewModel = manageTeamViewModel;
        this.teamViewModel = teamViewModel;
        this.createTaskViewModel = new CreateTaskViewModel();
    }

    @Override
    public void prepareSuccessView(TeamOutputData outputData) {
        //IDK what to do here. Leave empty for now
    }

    @Override
    public void prepareFailView(String errorMessage) {
        //IDK what to do here. Leave empty for now
    }

    @Override
    public void switchToLoggedInView() {
        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToManageTeamView() {
        //ManageTeamViewModel not yet implemented
    }

    @Override
    public void switchToCreateTaskView() {
        viewManagerModel.setState(createTaskViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
