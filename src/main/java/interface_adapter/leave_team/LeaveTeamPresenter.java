package interface_adapter.leave_team;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.leave_team.LeaveTeamOutputBoundary;
import use_case.leave_team.LeaveTeamOutputData;

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