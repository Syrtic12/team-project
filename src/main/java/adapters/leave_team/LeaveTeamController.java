package adapters.leave_team;

import usecase.leave_team.LeaveTeamInputBoundary;
import usecase.leave_team.LeaveTeamInputData;

public class LeaveTeamController {

    private final LeaveTeamInputBoundary interactor;

    public LeaveTeamController(LeaveTeamInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String teamId, String userId) {
        interactor.execute(new LeaveTeamInputData(teamId, userId));
    }
}

