package adapters.assign_task;

import adapters.team.TeamViewModel;
import usecase.assign_task.AssignTaskInputBoundary;
import usecase.assign_task.AssignTaskInputData;
import java.util.Map;

/**
 * Controller for the "Assign Task" use case.
 * Receives user input (task ID, team member email, team leader ID) from the view,
 * converts it into input data, and passes it to the input boundary (interactor)
 * to perform the assignment. Handles mapping from email to user ID using the team view model.
 */
public class AssignTaskController {
    private final AssignTaskInputBoundary assignTaskInteractor;
    private final TeamViewModel teamViewModel;

    public AssignTaskController(AssignTaskInputBoundary assignTaskInteractor, TeamViewModel teamViewModel) {
        this.assignTaskInteractor = assignTaskInteractor;
        this.teamViewModel = teamViewModel;
    }

    /**
     * Executes the "Assign Task" use case.
     * Looks up the team member ID from the provided email using the team view model.
     * If the email is not found in the team members, it assumes the email itself is the ID.
     * Creates an AssignTaskInputData object and passes it to the interactor.
     *
     * @param taskId the ID of the task to assign
     * @param memberEmail the email of the team member to assign
     * @param teamLeaderId the ID of the team leader performing the assignment
     */
    public void execute(String taskId, String memberEmail, String teamLeaderId) {
        final Map<String, String> teamMembers = teamViewModel.getState().getTeamMembers();
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

        final AssignTaskInputData inputData = new AssignTaskInputData(taskId, teamMemberId, teamLeaderId);
        assignTaskInteractor.execute(inputData);
    }
}
