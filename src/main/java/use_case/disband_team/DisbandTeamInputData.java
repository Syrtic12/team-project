package use_case.disband_team;

public class DisbandTeamInputData {

    private final String userId;
    private final String teamId;

    public DisbandTeamInputData(String userId, String teamId) {
        this.userId = userId;
        this.teamId = teamId;
    }

    public String getUserId() {
        return userId;
    }

    public String getTeamId() {
        return teamId;
    }
}
