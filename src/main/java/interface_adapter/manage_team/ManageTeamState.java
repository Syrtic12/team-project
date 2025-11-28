package interface_adapter.manage_team;

public class ManageTeamState {
    private String teamID;
    private String processError;
    private String action;

    public String getTeamID() {return teamID;}

    public void setTeamID(String teamID) {this.teamID = teamID;}

    public String getTeamError() {return this.processError;}

    public void setTeamError(String processError) {this.processError = processError;}

}
