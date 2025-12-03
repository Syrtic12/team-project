package adapters.leave_team;

import adapters.ViewModel;

/**
 * View model for the Leave Team UI.
 * Holds a LeaveTeamState object and provides identity information
 * used by the view manager to recognize this screen.
 */
public class LeaveTeamViewModel extends ViewModel<LeaveTeamState> {

    /**
     * Constructs the view model and initializes its state.
     *
     * @param leaveTeamState the initial state for the view model
     */
    public LeaveTeamViewModel(LeaveTeamState leaveTeamState) {
        super("logged in");
        setState(leaveTeamState);
    }
}
