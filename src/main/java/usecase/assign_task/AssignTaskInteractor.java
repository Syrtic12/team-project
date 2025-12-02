package usecase.assign_task;

import entity.Task;
import entity.User;
import entity.Team;

public class AssignTaskInteractor implements AssignTaskInputBoundary {

    private final AssignTaskDataAccessInterface dataAccessObject;
    private final AssignTaskOutputBoundary presenter;

    public AssignTaskInteractor(AssignTaskDataAccessInterface dataAccessObject, AssignTaskOutputBoundary presenter) {
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
        Team team = dataAccessObject.getTeamByTask(taskIdx);
        if (team == null) {
            presenter.prepareFailView("This task does not belong to any team");
            return;
        }
        if (!team.getLeaderIdx().equals(teamLeaderIdx)) {
            presenter.prepareFailView("Only team leader can assign task members");
            return;
        }
        boolean alreadyAssigned = dataAccessObject.isUserAssignedToTask(taskIdx, teamMemberIdx);
        if (alreadyAssigned) {
            presenter.prepareFailView("Team member already assigned to this task");
        } else {
            dataAccessObject.assignUserToTask(taskIdx, teamMemberIdx);
            Task updatedTask = dataAccessObject.getTask(taskIdx);
            AssignTaskOutputData outputData = new AssignTaskOutputData(taskIdx, teamMemberIdx, false, "success", updatedTask);
            presenter.prepareSuccessView(outputData);
        }
    }
}