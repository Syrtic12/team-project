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
        Optional<Team> teamOpt = dataAccess.getTeam(inputData.getTeamId());
        if (!teamOpt.isPresent()) {
            presenter.prepareFailView(new DeleteTaskOutputData(false, "Team not found"));
            return;
        }
        Team team = teamOpt.get();

        Optional<User> userOpt = dataAccess.getUser(inputData.getInvokedBy());
        if (!userOpt.isPresent()) {
            presenter.prepareFailView(new DeleteTaskOutputData(false, "User not found"));
            return;
        }
        User invoker = userOpt.get();

        // Check leader privileges (similar logic to create)
        boolean isLeader = false;
        try {
            Object leaderObj = team.getClass().getMethod("getLeader").invoke(team);
            if (leaderObj != null) isLeader = leaderObj.toString().equals(invoker.getIdx()) || leaderObj.toString().equals(invoker.getName());
        } catch (Exception ignore) {}

        if (!isLeader) {
            presenter.prepareFailView(new DeleteTaskOutputData(false, "Only team leader may delete tasks."));
            return;
        }

        Optional<Task> tOpt = dataAccess.getTaskByIdx(inputData.getTaskIdx());
        if (!tOpt.isPresent()) {
            presenter.prepareFailView(new DeleteTaskOutputData(false, "Task not found"));
            return;
        }
        Task t = tOpt.get();

        boolean removed = dataAccess.removeTask(t);
        if (!removed) {
            presenter.prepareFailView(new DeleteTaskOutputData(false, "Failed to delete task"));
            return;
        }
        dataAccess.detachTaskFromTeam(t, team);
        presenter.prepareSuccessView(new DeleteTaskOutputData(true, "Task deleted"));
    }
}
