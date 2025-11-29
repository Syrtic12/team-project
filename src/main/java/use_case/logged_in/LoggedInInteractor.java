package use_case.logged_in;

import entity.Task;
import entity.Team;
import entity.TeamFactory;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.login.LogInInputBoundary;
import use_case.login.LogInInputData;
import data_access.LoggedInDataAccessObject;
import data_access.KandoMongoDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoggedInInteractor implements LoggedInInputBoundary {
    private final LoggedInDataAccessInterface loggedInDataAccessObject;
    private final LoggedInOutputBoundary loggedInPresenter;
    public LoggedInInteractor(LoggedInDataAccessInterface loggedInDataAccessObject, LoggedInOutputBoundary loggedInPresenter) {
        this.loggedInDataAccessObject = loggedInDataAccessObject;
        this.loggedInPresenter = loggedInPresenter;

    }

    @Override
    public void execute(LoggedInInputData loggedInInputData) {

    }


    @Override
    public void switchToTeamView(String teamId) {
        //Step 1: Use ID to get task data from DB
        //Step 2: Get Task title and status
        //Step 3: filter by status into map, adding the ID and Title in that order
        //Step 4: Loop repeats for each task in LoggedInOutputData tasklist
        List<Task> tasks = loggedInDataAccessObject.getTeamTasks(teamId);
        Map<String, String> notStartedTasks = new HashMap<>();
        Map<String, String> inProgressTasks = new HashMap<>();
        Map<String, String> CompletedTasks = new HashMap<>();
        for (Task task : tasks) {
            if (task.getStatus()==0){
                notStartedTasks.put(task.getIdx(),task.getTitle());
            }
            else if (task.getStatus()==1){
                inProgressTasks.put(task.getIdx(),task.getTitle());
            }
            else if (task.getStatus()==2){
                CompletedTasks.put(task.getIdx(),task.getTitle());
            }
        }
        LoggedInOutputData outputData = new LoggedInOutputData(notStartedTasks,inProgressTasks,CompletedTasks,teamId);
        loggedInPresenter.switchToTeamView(outputData);
    }


    @Override
    public void switchToLoginView() {
        loggedInPresenter.switchToLoginView();
    }
}

