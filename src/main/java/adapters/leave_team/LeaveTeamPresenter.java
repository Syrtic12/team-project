package adapters.leave_team;

import adapters.ViewManagerModel;
import adapters.logged_in.LoggedInState;
import adapters.logged_in.LoggedInViewModel;
import usecase.leave_team.LeaveTeamOutputBoundary;
import usecase.leave_team.LeaveTeamOutputData;

/**
 * Presenter for the Leave Team use case.
 * This class takes output data from the interactor, updates the LeaveTeamViewModel
 * and LoggedInViewModel, and triggers navigation back to the logged-in view when
 * the operation succeeds.
 */
public class LeaveTeamPresenter implements LeaveTeamOutputBoundary {
    private final LeaveTeamViewModel leaveTeamViewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;

    /**
     * Constructs a LeaveTeamPresenter with the needed view models and manager.
     *
     * @param leaveTeamViewModel the view model holding leave team state
     * @param viewManagerModel   manages which view is currently active
     * @param loggedInViewModel  the view model for the logged-in view
     */
    public LeaveTeamPresenter(LeaveTeamViewModel leaveTeamViewModel,
                              ViewManagerModel viewManagerModel,
                              LoggedInViewModel loggedInViewModel) {
        this.leaveTeamViewModel = leaveTeamViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    /**
     * Updates state and navigates back to the logged-in view when the leave team
     * operation completes successfully.
     *
     * @param outputData the output data containing updated information and messages
     */
    @Override
    public void prepareSuccessView(LeaveTeamOutputData outputData) {
        final LeaveTeamState state = leaveTeamViewModel.getState();

        state.setTeamId(outputData.getTeamId());
        state.setUserId(outputData.getUserId());
        state.setSuccess(true);
        state.setMessage(outputData.getMessage());
        state.setTeams(outputData.getUpdatedTeams());
        leaveTeamViewModel.firePropertyChange();

        final LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setUserId(outputData.getUserId());
        loggedInState.setTeams(outputData.getUpdatedTeams());
        loggedInViewModel.firePropertyChange();

        switchToLoggedInView();
    }

    /**
     * Updates state when the leave team operation fails. Does not change views.
     *
     * @param outputData the output data containing error messages and identifiers
     */
    @Override
    public void prepareFailView(LeaveTeamOutputData outputData) {
        final LeaveTeamState state = leaveTeamViewModel.getState();
        state.setTeamId(outputData.getTeamId());
        state.setUserId(outputData.getUserId());
        state.setSuccess(false);
        state.setMessage(outputData.getMessage());

        leaveTeamViewModel.firePropertyChange();
    }

    /**
     * Switches the active UI view to the logged-in screen.
     */
    public void switchToLoggedInView() {
        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
