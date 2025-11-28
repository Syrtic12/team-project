package interface_adapter.manage_team;

import use_case.teammateManagement.TaskViewInputBoundary;
import use_case.teammateManagement.TeammateManagementInputData;

public class ManageTeamController {
    private final TaskViewInputBoundary TeamManagementInteractor;
    public ManageTeamController(TaskViewInputBoundary TeamManagementInteractor) {this.TeamManagementInteractor = TeamManagementInteractor;}
    public void execute(String teamID, String processError, String action) {
        final TeammateManagementInputData teammateManagementInputData = new TeammateManagementInputData(teamID, processError, action);
        TeamManagementInteractor.execute(teammateManagementInputData);
    }
}
