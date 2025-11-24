package use_case.disband_team;

public interface DisbandTeamInputBoundary {
    /**
     * Executes the disband team use case.
     * @param inputData the input data
     */
    void execute(DisbandTeamInputData inputData);
}
