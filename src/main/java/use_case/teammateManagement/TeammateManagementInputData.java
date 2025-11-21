package use_case.teammateManagement;

public class TeammateManagementInputData {
    private final String userID;
    private final String teamID;
    private final String action;

    public TeammateManagementInputData(String userID, String teamID, String action) {
        this.userID = userID;
        this.teamID = teamID;
        this.action = action;
    }

    String getUserID() {
        return userID;
    }

    String getTeamID() {
        return teamID;
    }

    String getAction() {
        return action;
    }
}
