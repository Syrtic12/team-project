package usecase.leave_team;

import java.util.List;

/**
 * Data that the logged in view will need after exiting this use case.
 */
public class LeaveTeamOutputData {
    private final boolean success;
    private final String teamId;
    private final String userId;
    private final String message;
    private final List<String> updatedTeams;

    public LeaveTeamOutputData(boolean success, String teamId, String userId, String message,
                               List<String> updatedTeams) {
        this.success = success;
        this.teamId = teamId;
        this.userId = userId;
        this.message = message;
        this.updatedTeams = updatedTeams;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getUserId() {
        return userId;
    }

    public String getTeamId() {
        return teamId;
    }

    public List<String> getUpdatedTeams() {
        return updatedTeams;
    }

}
