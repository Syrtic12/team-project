package interface_adapter.leave_team;

import use_case.leave_team.LeaveTeamInputBoundary;
import use_case.leave_team.LeaveTeamInputData;

public class LeaveTeamController {

    private final LeaveTeamInputBoundary interactor;

    public LeaveTeamController(LeaveTeamInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String teamId, String userId) {
        interactor.execute(new LeaveTeamInputData(teamId, userId));
    }
}