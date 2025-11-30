package use_case.logged_in;

import entity.Task;
import interface_adapter.logged_in.LoggedInState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoggedInInteractor implements LoggedInInputBoundary {

    private final LoggedInDataAccessInterface loggedInDataAccessObject;
    private final LoggedInOutputBoundary loggedInPresenter;
    private final LoggedInState loggedInState;

    public LoggedInInteractor(LoggedInDataAccessInterface loggedInDataAccessObject,
                              LoggedInOutputBoundary loggedInPresenter,
                              LoggedInState loggedInState) {
        this.loggedInDataAccessObject = loggedInDataAccessObject;
        this.loggedInPresenter = loggedInPresenter;
        this.loggedInState = loggedInState;
    }

    @Override
    public void execute(LoggedInInputData loggedInInputData) {
        // not used
    }

    @Override
    public void switchToTeamView(String teamId) {

        // 1. Get tasks
        List<Task> tasks = loggedInDataAccessObject.getTeamTasks(teamId);

        Map<String, String> notStartedTasks = new HashMap<>();
        Map<String, String> inProgressTasks = new HashMap<>();
        Map<String, String> CompletedTasks = new HashMap<>();

        for (Task task : tasks) {
            if (task.getStatus() == 0) {
                notStartedTasks.put(task.getIdx(), task.getTitle());
            } else if (task.getStatus() == 1) {
                inProgressTasks.put(task.getIdx(), task.getTitle());
            } else {
                CompletedTasks.put(task.getIdx(), task.getTitle());
            }
        }

        // ⭐ 2. Read the current logged-in user's ID
        String userId = loggedInState.getUserId();

        // ⭐ 3. Pass userId into output data
        LoggedInOutputData outputData =
                new LoggedInOutputData(
                        notStartedTasks,
                        inProgressTasks,
                        CompletedTasks,
                        teamId,
                        userId
                );

        loggedInPresenter.switchToTeamView(outputData);
    }

    @Override
    public void switchToLoginView() {
        loggedInPresenter.switchToLoginView();
    }
}
