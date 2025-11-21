package use_case.assign_task;

import entity.Task;
import entity.User;

public class AssignTaskInteractor implements AssignTaskInputBoundary{

    private final AssignTaskDataAccessInterface dataAccessObject;
    private final AssignTaskOutputBoundary presenter;

    public AssignTaskInteractor(AssignTaskDataAccessInterface dataAccessObject,  AssignTaskOutputBoundary presenter) {
        this.dataAccessObject = dataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute(AssignTaskInputData assignTaskInputData) {
        String taskIdx = assignTaskInputData.getTaskIdx();
        String teamMemberIdx = assignTaskInputData.getTeamMemberIdx();
        String teamLeaderIdx = assignTaskInputData.getTeamLeaderIdx();

        Task task = dataAccessObject.getTask(taskIdx);
        if (task == null) {
            presenter.prepareFailView("Task not found");
            return;
        }

        User teamMember = dataAccessObject.getUser(teamMemberIdx);
        if (teamMember == null) {
            presenter.prepareFailView("Team member not found");
            return;
        }

        User teamLeader = dataAccessObject.getUser(teamLeaderIdx);
        if (teamLeader == null) {
            presenter.prepareFailView("Team Leader not found");
            return;
        }

        boolean alreadyAssigned = dataAccessObject.isUserAssignedToTask(taskIdx, teamMemberIdx);
        if (alreadyAssigned) {
            presenter.prepareFailView("Team member already assigned to this task");
        } else {
            dataAccessObject.assignUserToTask(taskIdx, teamMemberIdx);
            AssignTaskOutputData outputData = new AssignTaskOutputData(taskIdx, teamMemberIdx, false, "success");
            presenter.prepareSuccessView(outputData);
        }
    }
}
