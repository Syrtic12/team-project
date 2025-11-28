package interface_adapter.manage_team;

import java.util.ArrayList;
import java.util.List;

public class ManageTeamState {
    private String teamId = "";
    private String newMemberEmail = "";
    private List<String> members = new ArrayList<>();
    private String error;

    public ManageTeamState(ManageTeamState copy) {
        teamId = copy.teamId;
        members = new ArrayList<>(copy.members);
        newMemberEmail = copy.newMemberEmail;
        error = copy.error;
    }

   public ManageTeamState() {
   }

   public String getTeamId() {
        return teamId;
   }

   public void setTeamId(String teamId) {
        this.teamId = teamId;
   }

   public List<String> getMembers() {
        return members;
   }

   public void setMembers(List<String> members) {
        this.members = members;
   }

   public String getNewMemberEmail() {
        return newMemberEmail;
   }

   public void setNewMemberEmail(String newMemberEmail) {
        this.newMemberEmail = newMemberEmail;
   }

   public String getError() {
        return error;
   }

   public void setError(String error) {
        this.error = error;
   }
}
