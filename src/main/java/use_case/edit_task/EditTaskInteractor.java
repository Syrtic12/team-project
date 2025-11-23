package use_case.edit_task;

import entity.Task;
import entity.Team;
import entity.User;
import java.util.Optional;

public class EditTaskInteractor implements EditTaskInputBoundary {

    private final EditTaskDataAccessInterface dataAccess;
    private final EditTaskOutputBoundary presenter;

    public EditTaskInteractor(EditTaskDataAccessInterface dataAccess, EditTaskOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(EditTaskInputData inputData) {
        Optional<Task> tOpt = dataAccess.getTaskByIdx(inputData.getTaskIdx());
        if (!tOpt.isPresent()) {
            presenter.prepareFailView(new EditTaskOutputData(false, "Task not found"));
            return;
        }
        Task t = tOpt.get();

        Optional<Team> teamOpt = dataAccess.getTeam(inputData.getTeamId());
        Team team = teamOpt.orElse(null);

        Optional<User> userOpt = dataAccess.getUser(inputData.getInvokedBy());
        if (!userOpt.isPresent()) {
            presenter.prepareFailView(new EditTaskOutputData(false, "User not found"));
            return;
        }
        User invoker = userOpt.get();

        // Authorization: assigned user OR team leader
        boolean assigned = t.isAssignedUser(inputData.getInvokedBy());
        boolean isLeader = false;
        if (team != null) {
            try {
                Object leaderObj = team.getClass().getMethod("getLeader").invoke(team);
                if (leaderObj != null)
                    isLeader = leaderObj.toString().equals(invoker.getIdx()) || leaderObj.toString().equals(invoker.getName());
            } catch (Exception ignore) {}
        }

        if (!assigned && !isLeader) {
            presenter.prepareFailView(new EditTaskOutputData(false, "Only an assigned user or team leader may edit the task."));
            return;
        }

        if (inputData.getNewTitle() != null) t.setTitle(inputData.getNewTitle());
        if (inputData.getNewDescription() != null) t.setDescription(inputData.getNewDescription());
        if (inputData.getNewStatus() != null) t.changeStatus(inputData.getNewStatus());

        dataAccess.saveTask(t);
        presenter.prepareSuccessView(new EditTaskOutputData(true, "Task updated"));
    }
}
