package adapters.leave_team;

import java.util.List;

public class LeaveTeamState {

    private String teamId;
    private String userId;
    private boolean success;
    private String message;
    private List<String> teamMembers;

    public LeaveTeamState() {}

    public String getTeamId() {
        return teamId;
    }
    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public void setTeams(List<String> updatedTeams) {
        this.teamMembers = updatedTeams;
    }
}
