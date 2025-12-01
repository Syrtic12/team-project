package adapters.manage_team;

import adapters.ViewManagerModel;
import adapters.logged_in.LoggedInViewModel;
import adapters.team.TeamViewModel;
import usecase.teammate_management.TeammateManagementOutputBoundary;
import usecase.teammate_management.TeammateManagementOutputData;

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
        manageTeamState.setError(null);
        manageTeamViewModel.firePropertyChange();
    }

    @Override
    public void switchToTeamView(){
        viewManagerModel.setState(teamViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
