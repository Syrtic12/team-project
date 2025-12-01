package usecase.teammate_management;

public class TeammateManagementInputData {
    private final String email;
    private final String teamID;
    private final String action;

    public TeammateManagementInputData(String email, String teamID, String action) {
        this.email = email;
        this.teamID = teamID;
        this.action = action;
    }

    String getEmail() {
        return email;
    }

    String getTeamID() {
        return teamID;
    }

    String getAction() {
        return action;
    }
}
