package use_case.create_team;

public interface CreateTeamOutputBoundary {
    /**
     * Present successful team creation result.
     * @param outputData the output data
     */
    void prepareSuccessView(CreateTeamOutputData outputData);

    /**
     * Present failure message (e.g., team name already exists).
     * @param errorMessage the failure message
     */
    void prepareFailView(String errorMessage);
}
