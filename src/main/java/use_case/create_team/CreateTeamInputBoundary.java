package use_case.create_team;

public interface CreateTeamInputBoundary {
    /**
     * Executes the create team use case.
     * @param inputData the input data
     */
    void execute(CreateTeamInputData inputData);
}
