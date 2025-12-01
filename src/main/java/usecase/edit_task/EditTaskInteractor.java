package usecase.edit_task;

import entity.Task;
import entity.Team;
import entity.User;

import java.util.Optional;

public class EditTaskInteractor implements EditTaskInputBoundary {

    private final EditTaskDataAccessInterface dataAccess;
    private final EditTaskOutputBoundary presenter;

    public EditTaskInteractor(EditTaskDataAccessInterface dataAccess,
                              EditTaskOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(EditTaskInputData inputData) {

        // 1. Task check
        Optional<Task> taskOpt = dataAccess.getTaskByIdx(inputData.getTaskIdx());
        if (!taskOpt.isPresent()) {
            presenter.prepareFailView(
                    new EditTaskOutputData(false, "Task not found", null, null));
            return;
        }
        Task task = taskOpt.get();

        Integer oldStatus = task.getStatus();

        // 2. User check
        Optional<User> userOpt = dataAccess.getUser(inputData.getInvokedBy());
        if (!userOpt.isPresent()) {
            presenter.prepareFailView(
                    new EditTaskOutputData(false, "User not found", null, null));
            return;
        }
        User user = userOpt.get();

        // 3. Team check 
        Optional<Team> teamOpt = dataAccess.getTeam(inputData.getTeamId());
        if (!teamOpt.isPresent()) {
            presenter.prepareFailView(
                    new EditTaskOutputData(false, "Team not found", null, null));
            return;
        }
        Team team = teamOpt.get();

        // 4. Authorization
        boolean isAssigned = task.isAssignedUser(user.getIdx());
        boolean isLeader = team.getLeaderIdx().equals(user.getIdx());

        if (!isAssigned && !isLeader) {
            presenter.prepareFailView(
                    new EditTaskOutputData(false,
                            "Only an assigned user or team leader may edit the task.", null, null));
            return;
        }

        // 5. Apply edits
        if (inputData.getNewTitle() != null)
            task.setTitle(inputData.getNewTitle());

        if (inputData.getNewDescription() != null)
            task.setDescription(inputData.getNewDescription());

        if (inputData.getNewStatus() != null)
            task.changeStatus(inputData.getNewStatus());

        // 6. Persist
        dataAccess.saveTask(task);

        presenter.prepareSuccessView(
                new EditTaskOutputData(true, "Task updated", task, oldStatus));
    }

    @Override
    public void switchToTeamView() {
        presenter.switchToTeamView();
    }
}
