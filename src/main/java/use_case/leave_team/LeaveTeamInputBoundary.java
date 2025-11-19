package use_case.leave_team;

import use_case.login.LogInInputData;

public interface LeaveTeamInputBoundary {
    /**
     * Executes the leaveTeam use case.
     * @param leaveTeamInputData the input data
     */
    void execute(LeaveTeamInputData leaveTeamInputData);
}
