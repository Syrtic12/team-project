package usecase.leave_team;

public interface LeaveTeamInputBoundary {
    /**
     * Executes the leaveTeam use case.
     * @param leaveTeamInputData the input data
     */
    void execute(LeaveTeamInputData leaveTeamInputData);
}
