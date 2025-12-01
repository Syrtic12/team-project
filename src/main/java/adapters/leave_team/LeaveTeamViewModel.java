package adapters.leave_team;

import adapters.ViewModel;

public class LeaveTeamViewModel extends ViewModel<LeaveTeamState> {
    public LeaveTeamViewModel(LeaveTeamState leaveTeamState) {
        super("logged in");
        setState(leaveTeamState);
    }

}
