package usecase.disband_team;

public class DisbandTeamInteractor implements DisbandTeamInputBoundary {

    private final DisbandTeamDataAccessInterface dataAccess;
    private final DisbandTeamOutputBoundary outputBoundary;

    public DisbandTeamInteractor(DisbandTeamDataAccessInterface dataAccess,
                                 DisbandTeamOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(DisbandTeamInputData inputData) {

        String userId = inputData.getUserId();
        String teamId = inputData.getTeamId();

        if (!dataAccess.teamExists(userId, teamId)) {
            outputBoundary.prepareFailView("Team does not exist or does not belong to this user.");
            return;
        }

        dataAccess.deleteTeam(teamId);

        DisbandTeamOutputData outputData = new DisbandTeamOutputData(teamId, true);
        outputBoundary.prepareSuccessView(outputData);
    }
}
