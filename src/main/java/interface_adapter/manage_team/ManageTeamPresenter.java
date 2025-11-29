package interface_adapter.manage_team;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.team.TeamViewModel;
import use_case.teammateManagement.TeammateManagementOutputBoundary;
import use_case.teammateManagement.TeammateManagementOutputData;
import view.ManageTeamView;

public class ManageTeamPresenter implements TeammateManagementOutputBoundary {
    private final ManageTeamViewModel manageTeamViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;
    private final TeamViewModel teamViewModel;
    public ManageTeamPresenter(ViewManagerModel viewManagerModel, LoggedInViewModel loggedInViewModel,
                               ManageTeamViewModel manageTeamViewModel, TeamViewModel teamViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.teamViewModel = teamViewModel;
        this.manageTeamViewModel = manageTeamViewModel;
    }


    @Override
    public void prepareSuccessView(TeammateManagementOutputData outputData) {
        // On success, update the ManageTeamViewModel state to better reflect the new team roster
        // The ManageTeamView should stay open
        final ManageTeamState manageTeamState = manageTeamViewModel.getState();
        manageTeamState.setMembers(outputData.getMemberList());
        this.manageTeamViewModel.firePropertyChange();

    }

    @Override
    public void prepareFailView(String errorMessage) {
        final ManageTeamState manageTeamState = manageTeamViewModel.getState();
        manageTeamState.setError(errorMessage);
        manageTeamViewModel.firePropertyChange();
    }

    @Override
    public void switchToTeamView(){
        viewManagerModel.setState(teamViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
