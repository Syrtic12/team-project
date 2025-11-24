package use_case.create_team;

public class CreateTeamInputData {
    private final String teamName;
    private final String userId;

    public CreateTeamInputData(String teamName, String userId) {
        this.teamName = teamName;
        this.userId = userId;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getUserId() {
        return userId;
    }
}
