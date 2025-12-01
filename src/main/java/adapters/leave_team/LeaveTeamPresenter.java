package adapters.leave_team;

import adapters.ViewManagerModel;
import adapters.logged_in.LoggedInState;
import adapters.logged_in.LoggedInViewModel;
import usecase.leave_team.LeaveTeamOutputBoundary;
import usecase.leave_team.LeaveTeamOutputData;

public class LeaveTeamPresenter implements LeaveTeamOutputBoundary {
    private final LeaveTeamViewModel leaveTeamViewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;

    public LeaveTeamPresenter(LeaveTeamViewModel leaveTeamViewModel,
                              ViewManagerModel viewManagerModel,
                              LoggedInViewModel loggedInViewModel) {
        this.leaveTeamViewModel = leaveTeamViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void prepareSuccessView(LeaveTeamOutputData outputData) {
        LeaveTeamState state = leaveTeamViewModel.getState();

        state.setTeamId(outputData.getTeamId());
        state.setUserId(outputData.getUserId());
        state.setSuccess(true);
        state.setMessage(outputData.getMessage());
        state.setTeams(outputData.getUpdatedTeams());
        leaveTeamViewModel.firePropertyChange();

        LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setUserId(outputData.getUserId());
        loggedInState.setTeams(outputData.getUpdatedTeams());
        loggedInViewModel.firePropertyChange();

        switchToLoggedInView();
    }

    @Override
    public void prepareFailView(LeaveTeamOutputData outputData) {
        LeaveTeamState state = leaveTeamViewModel.getState();
        state.setTeamId(outputData.getTeamId());
        state.setUserId(outputData.getUserId());
        state.setSuccess(false);
        state.setMessage(outputData.getMessage());

        leaveTeamViewModel.firePropertyChange();
    }

    public void switchToLoggedInView() {
        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}


