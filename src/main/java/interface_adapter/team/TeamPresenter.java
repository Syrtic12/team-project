package interface_adapter.team;

import interface_adapter.ViewManagerModel;
import interface_adapter.team.TeamState;
import interface_adapter.team.TeamViewModel;
import interface_adapter.manage_team.ManageTeamViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.create_task.CreateTaskViewModel;

public class TeamPresenter{
    private final ViewManagerModel viewManagerModel;
    private final TeamState teamState;
    private final TeamViewModel teamViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final ManageTeamViewModel manageTeamViewModel;
    private final CreateTaskViewModel createTaskViewModel;

    public TeamPresenter(ViewManagerModel viewManagerModel, TeamState teamState, LoggedInViewModel loggedInViewModel,
                         ManageTeamViewModel manageTeamViewModel, CreateTaskViewModel createTaskViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.teamState = teamState;
        this.teamViewModel = new TeamViewModel();
        this.loggedInViewModel = loggedInViewModel;
        this.manageTeamViewModel = manageTeamViewModel;
        this.createTaskViewModel = createTaskViewModel;
    }


    public void openManageTeam() {
        viewManagerModel.setState(manageTeamViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    public void openCreateTask() {
        viewManagerModel.setState(createTaskViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    public void openLoggedInView() {
        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    public void openTask(String taskId) {

    }

}
