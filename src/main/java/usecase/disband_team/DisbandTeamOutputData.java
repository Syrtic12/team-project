package usecase.disband_team;

public class DisbandTeamOutputData {

    private final String teamId;
    private final boolean success;

    public DisbandTeamOutputData(String teamId, boolean success) {
        this.teamId = teamId;
        this.success = success;
    }

    public String getTeamId() {
        return teamId;
    }

    public boolean isSuccess() {
        return success;
    }
}
