package usecase.assign_task;

import entity.Task;
import entity.User;
import entity.Team;

/**
 * Interactor class for the "Assign Task" use case.
 * Handles the assignment of a team member to a task by verifying task,
 * team member, team leader, and team data. Uses the input boundary to
 * receive data and the output boundary to present success or failure.
 */
public class AssignTaskInteractor implements AssignTaskInputBoundary {

    private final AssignTaskDataAccessInterface dataAccessObject;
    private final AssignTaskOutputBoundary presenter;

    public AssignTaskInteractor(AssignTaskDataAccessInterface dataAccessObject, AssignTaskOutputBoundary presenter) {
        this.dataAccessObject = dataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute(AssignTaskInputData inputData) {
        final String taskIdx = inputData.getTaskIdx();
        final String teamMemberIdx = inputData.getTeamMemberIdx();
        final String teamLeaderIdx = inputData.getTeamLeaderIdx();

        final Task task = getTaskOrFail(taskIdx);
        final User teamMember = getUserOrFail(teamMemberIdx, "Team member not found");
        final User teamLeader = getUserOrFail(teamLeaderIdx, "Team leader not found");
        final Team team = getTeamOrFail(taskIdx);

        if (task != null && teamMember != null && teamLeader != null && team != null) {
            if (!team.getLeaderIdx().equals(teamLeaderIdx)) {
                prepareFail("Only the team leader can assign task members");
            }
            else if (dataAccessObject.isUserAssignedToTask(taskIdx, teamMemberIdx)) {
                prepareFail("Team member already assigned to this task");
            }
            else {
                dataAccessObject.assignUserToTask(taskIdx, teamMemberIdx);
                final Task updatedTask = dataAccessObject.getTask(taskIdx);
                prepareSuccess(taskIdx, teamMemberIdx, updatedTask);
            }
        }
    }

    private Task getTaskOrFail(String taskIdx) {
        final Task task = dataAccessObject.getTask(taskIdx);
        if (task == null) {
            prepareFail("Task not found");
        }
        return task;
    }

    private User getUserOrFail(String userIdx, String message) {
        final User user = dataAccessObject.getUser(userIdx);
        if (user == null) {
            prepareFail(message);
        }
        return user;
    }

    private Team getTeamOrFail(String taskIdx) {
        final Team team = dataAccessObject.getTeamByTask(taskIdx);
        if (team == null) {
            prepareFail("This task does not belong to any team");
        }
        return team;
    }

    private void prepareFail(String message) {
        presenter.prepareFailView(message);
    }

    private void prepareSuccess(String taskIdx, String memberIdx, Task updatedTask) {
        final AssignTaskOutputData outputData = new AssignTaskOutputData(
                taskIdx,
                memberIdx,
                false,
                "success",
                updatedTask
        );
        presenter.prepareSuccessView(outputData);
    }

}
