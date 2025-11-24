package use_case.delete_task;

import entity.Task;
import entity.Team;
import entity.User;

import java.util.Optional;

public class DeleteTaskInteractor implements DeleteTaskInputBoundary {

    private final DeleteTaskDataAccessInterface dataAccess;
    private final DeleteTaskOutputBoundary presenter;

    public DeleteTaskInteractor(DeleteTaskDataAccessInterface dataAccess,
                                DeleteTaskOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(DeleteTaskInputData inputData) {

        // 1. TEAM MUST EXIST
        Optional<Team> teamOpt = dataAccess.getTeam(inputData.getTeamId());
        if (teamOpt.isEmpty()) {
            presenter.prepareFailView(new DeleteTaskOutputData(false, "Team not found"));
            return;
        }
        Team team = teamOpt.get();

        // 2. USER MUST EXIST
        Optional<User> userOpt = dataAccess.getUser(inputData.getInvokedBy());
        if (userOpt.isEmpty()) {
            presenter.prepareFailView(new DeleteTaskOutputData(false, "User not found"));
            return;
        }
        User invoker = userOpt.get();

        // 3. TASK MUST EXIST (TESTS EXPECT THIS BEFORE LEADER CHECK)
        Optional<Task> taskOpt = dataAccess.getTaskByIdx(inputData.getTaskIdx());
        if (taskOpt.isEmpty()) {
            presenter.prepareFailView(new DeleteTaskOutputData(false, "Task not found"));
            return;
        }
        Task task = taskOpt.get();

        // 4. CHECK LEADER AFTER TASK FOUND
        boolean isLeader =
                team.getLeader() != null &&
                team.getLeader().getIdx().equals(invoker.getIdx());

        if (!isLeader) {
            presenter.prepareFailView(new DeleteTaskOutputData(false,
                    "Only team leader may delete tasks."));
            return;
        }

        // 5. DELETE THE TASK
        boolean removed = dataAccess.removeTask(task);
        if (!removed) {
            presenter.prepareFailView(new DeleteTaskOutputData(false,
                    "Failed to delete task"));
            return;
        }

        // 6. DETACH FROM TEAM
        dataAccess.detachTaskFromTeam(task, team);

        presenter.prepareSuccessView(
                new DeleteTaskOutputData(true, "Task deleted"));
    }
}
