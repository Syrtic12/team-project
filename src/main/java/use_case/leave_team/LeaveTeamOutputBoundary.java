package use_case.leave_team;

public interface LeaveTeamOutputBoundary {
    /**
     * Prepares the success view.
     * @param outputData the output data
     */
    void prepareSuccessView(LeaveTeamOutputData outputData);

    /**
     * Prepares the failure view.
     * @param outputData output data with an associated error message
     */
    void prepareFailView(LeaveTeamOutputData outputData);
}
