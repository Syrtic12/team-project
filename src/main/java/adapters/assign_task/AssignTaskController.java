package adapters.assign_task;

import interface_adapter.team.TeamViewModel;
import use_case.assign_task.AssignTaskInputBoundary;
import use_case.assign_task.AssignTaskInputData;
import java.util.Map;

public class AssignTaskController {
    private final AssignTaskInputBoundary assignTaskInteractor;
    private final TeamViewModel teamViewModel;

    public AssignTaskController(AssignTaskInputBoundary assignTaskInteractor, TeamViewModel teamViewModel) {
        this.assignTaskInteractor = assignTaskInteractor;
        this.teamViewModel = teamViewModel;
    }

    public void execute(String taskId, String memberEmail, String teamLeaderId) {
        Map<String, String> teamMembers = teamViewModel.getState().getTeamMembers();
        String teamMemberId = null;

        for (Map.Entry<String, String> entry : teamMembers.entrySet()) {
            if (entry.getValue().equals(memberEmail)) {
                teamMemberId = entry.getKey();
                break;
            }
        }

        if (teamMemberId == null) {
            teamMemberId = memberEmail;
        }

        AssignTaskInputData inputData = new AssignTaskInputData(taskId, teamMemberId, teamLeaderId);
        assignTaskInteractor.execute(inputData);
    }
}
