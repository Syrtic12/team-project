package adapters.leave_team;

import java.util.List;

/**
 * State container for the Leave Team view.
 * Stores identifiers, success status, messages, and the updated list
 * of the user's teams after leaving a team.
 */
public class LeaveTeamState {

    private String teamId;
    private String userId;
    private boolean success;
    private String message;
    private List<String> teamMembers;

    /**
     * Creates an empty LeaveTeamState.
     */
    public LeaveTeamState() {
        // not needed
    }

    /**
     * Gives the teamID.
     *
     * @return the ID of the team the user attempted to leave
     */
    public String getTeamId() {
        return teamId;
    }

    /**
     * Sets the ID of the team the user attempted to leave.
     *
     * @param teamId the team ID
     */
    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    /**
     *  Gives the user id.
     *
     * @return the ID of the user performing the operation
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user performing the operation.
     *
     * @param userId the user ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Returns whether leaving was a success.
     *
     * @return true if the operation was successful, false otherwise
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets whether the leave team operation succeeded.
     *
     * @param success true if successful
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Returns the associated message.
     *
     * @return a status or error message describing the result
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets a message describing the outcome of the operation.
     *
     * @param message status message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Updates the user's list of teams after leaving a team.
     *
     * @param updatedTeams the updated collection of teams
     */
    public void setTeams(List<String> updatedTeams) {
        this.teamMembers = updatedTeams;
    }

    /**
     * Updates the user's list of teams after leaving a team.
     *
     * @return the list of team members in the team
     */
    public List<String> getTeamMembers() {
        return teamMembers;
    }
}
