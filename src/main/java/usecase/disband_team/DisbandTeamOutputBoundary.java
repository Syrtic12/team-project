package usecase.disband_team;

public interface DisbandTeamOutputBoundary {
    /**
     * Prepare a view for successful disband.
     */
    void prepareSuccessView(DisbandTeamOutputData outputData);

    /**
     * Prepare a view for failed disband.
     */
    void prepareFailView(String error);
}
