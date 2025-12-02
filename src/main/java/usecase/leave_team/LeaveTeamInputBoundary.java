package usecase.leave_team;

/**
 * Serves as the input boundary for the leave team use case.
 */
public interface LeaveTeamInputBoundary {
    /**
     * Executes the leaveTeam use case.
     * @param leaveTeamInputData the input data
     */
    void execute(LeaveTeamInputData leaveTeamInputData);
}
