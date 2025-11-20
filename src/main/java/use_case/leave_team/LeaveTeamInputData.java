package use_case.leave_team;

public class LeaveTeamInputData {
    private final String teamId;
    private final String userId;

    public LeaveTeamInputData(String teamId, String userId) {
        this.teamId = teamId;
        this.userId = userId;
    }

    String getUserId() {
        return userId;
    }

    String getTeamId() {
        return teamId;
    }
}
