package use_case.logged_in;

import entity.Task;
import interface_adapter.logged_in.LoggedInState;

import entity.Team;
import entity.TeamFactory;
import entity.User;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.login.LogInInputBoundary;
import use_case.login.LogInInputData;
import data_access.LoggedInDataAccessObject;
import data_access.KandoMongoDatabase;
import use_case.team.TaskInfo;

import java.util.ArrayList;
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
        Map<String, TaskInfo> notStartedTasks = new HashMap<>();
        Map<String, TaskInfo> inProgressTasks = new HashMap<>();
        Map<String, TaskInfo> CompletedTasks = new HashMap<>();

        for (Task task : tasks) {
            List<String> assignedUsers = task.getAssignedUsers();
            String names = String.join(", ", assignedUsers);
            if (assignedUsers.isEmpty()) {
                names = "no users assigned";
            }
            TaskInfo info = new TaskInfo(task.getIdx(), task.getTitle(), task.getDescription(), names);

            if (task.getStatus()==0){
                notStartedTasks.put(task.getIdx(),info);
            }
            else if (task.getStatus()==1){
                inProgressTasks.put(task.getIdx(), info);
            }
            else if (task.getStatus()==2){
                CompletedTasks.put(task.getIdx(), info);
            }
        }

        // 2. Read the current logged-in user's ID
        String userId = loggedInState.getUserId();

        Map<String, String> teamMembers = loggedInDataAccessObject.getTeamMembers(teamId);
        String leaderId = loggedInDataAccessObject.getTeamLeaderId(teamId);

        // 3. Pass userId into output data
        LoggedInOutputData outputData =
                new LoggedInOutputData(
                        notStartedTasks,
                        inProgressTasks,
                        CompletedTasks,
                        teamId,
                        userId,
                        leaderId,
                        teamMembers
                );

        loggedInPresenter.switchToTeamView(outputData);
    }

    @Override
    public void switchToLoginView() {
        loggedInPresenter.switchToLoginView();
    }
}
