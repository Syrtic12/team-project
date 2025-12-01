package usecase.leave_team;

public class LeaveTeamOutputData {
    private final boolean success;
    private final String teamId;
    private final String userId;
    private final String message;

    public LeaveTeamOutputData(boolean success, String teamId, String userId, String message) {
        this.success = success;
        this.teamId = teamId;
        this.userId = userId;
        this.message = message;
    }

    public boolean isSuccess() { return success; }

    public String getMessage() { return message; }

    public String getUserId() { return userId; }

    public String getTeamId() { return teamId; }
}
