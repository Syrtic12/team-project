package adapters.manage_team;

import usecase.teammate_management.TeammateManagementInputBoundary;
import usecase.teammate_management.TeammateManagementInputData;

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
