package interface_adapter.leave_team;

import interface_adapter.ViewModel;
public class LeaveTeamViewModel extends ViewModel<LeaveTeamState> {
    public LeaveTeamViewModel(LeaveTeamState leaveTeamState) {
        super("logged in");
        setState(leaveTeamState);
    }

}
