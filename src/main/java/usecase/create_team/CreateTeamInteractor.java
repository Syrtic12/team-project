package usecase.create_team;

public class CreateTeamInteractor implements CreateTeamInputBoundary {

    private final CreateTeamDataAccessInterface dataAccess;
    private final CreateTeamOutputBoundary outputBoundary;

    public CreateTeamInteractor(CreateTeamDataAccessInterface dataAccess,
                                CreateTeamOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(CreateTeamInputData inputData) {

        String userId = inputData.getUserId();
        String teamName = inputData.getTeamName();

        // 1. Check if the team name already exists for this user
        if (dataAccess.teamNameExists(userId, teamName)) {
            outputBoundary.prepareFailView(
                    "Team name already exists for this user."
            );
            return;
        }

        // 2. Save the team
        dataAccess.saveTeam(userId, teamName);

        // 3. Obtain leader name
        String leaderName = dataAccess.getLeaderName(userId);

        // 4. Generate output data
        CreateTeamOutputData outputData =
                new CreateTeamOutputData(teamName, leaderName);

        // 5. Call presenter
        outputBoundary.prepareSuccessView(outputData);
    }


    /* ------------------------------------------------------
       Simple quick test (as required by instructor)
       ------------------------------------------------------ */
    public static void main(String[] args) {

        // Fake Repo
        CreateTeamDataAccessInterface repo = new CreateTeamDataAccessInterface() {

            private String savedTeam = null;

            @Override
            public boolean teamNameExists(String userId, String teamName) {
                return savedTeam != null && savedTeam.equals(teamName);
            }

            @Override
            public void saveTeam(String userId, String teamName) {
                savedTeam = teamName;
                System.out.println("Saved team to repo: " + teamName);
            }

            @Override
            public String getLeaderName(String userId) {
                return "Tony Stark"; // fake leader name
            }
        };

        // Fake Presenter
        CreateTeamOutputBoundary presenter = new CreateTeamOutputBoundary() {

            @Override
            public void prepareSuccessView(CreateTeamOutputData outputData) {
                System.out.println("SUCCESS:");
                System.out.println("Team: " + outputData.getTeamName());
                System.out.println("Leader: " + outputData.getLeaderName());
            }

            @Override
            public void prepareFailView(String error) {
                System.out.println("FAILED: " + error);
            }
        };

        // Interactor
        CreateTeamInputBoundary interactor =
                new CreateTeamInteractor(repo, presenter);

        // Fake input
        CreateTeamInputData input =
                new CreateTeamInputData("user123", "Avengers");

        // Execute
        interactor.execute(input);
    }
}

