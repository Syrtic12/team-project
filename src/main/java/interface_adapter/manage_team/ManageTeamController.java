package interface_adapter.manage_team;

import use_case.teammateManagement.TeammateManagementInputBoundary;
import use_case.teammateManagement.TeammateManagementInputBoundary;
import use_case.teammateManagement.TeammateManagementInputData;

public class ManageTeamController {
    private final TeammateManagementInputBoundary teammateManagementInteractor;
    public ManageTeamController(TeammateManagementInputBoundary teammateManagementInteractor) {
        this.teammateManagementInteractor = teammateManagementInteractor; }

    public void execute(String userID, String teamID, String action){
        final TeammateManagementInputData teammateManagementInputData =
                new TeammateManagementInputData(userID, teamID, action);
        teammateManagementInteractor.execute(teammateManagementInputData);
    }

    public void switchToTeamView() {
        teammateManagementInteractor.switchToTeamView();
    }

    public void disbandTeam() {

    }
}
