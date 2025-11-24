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
import java.util.List;

public class LoggedInInteractor implements LoggedInInputBoundary {
    private final LoggedInDataAccessInterface loggedInDataAccessObject;
    private final LoggedInOutputBoundary loggedInPresenter;
    public LoggedInInteractor(LoggedInDataAccessInterface loggedInDataAccessObject, LoggedInOutputBoundary loggedInPresenter) {
        this.loggedInDataAccessObject = loggedInDataAccessObject;
        this.loggedInPresenter = loggedInPresenter;

    }

    @Override
    public void execute(LoggedInInputData loggedInInputData) {
        //Step 1: Use ID to get task data from DB
        //Step 2: Get Task title and status
        //Step 3: filter by status into map, adding the ID and Title in that order
        //Step 4: Loop repeats for each task in LoggedInOutputData tasklist
        List<String> taskIDs;
        for (String teamID : loggedInInputData.getTeams()) {
            Team team = loggedInDataAccessObject.getTeamTasks(teamID);

        }

    }


    @Override
    public void switchToTeamView(String teamId) {
        loggedInPresenter.switchToTeamView(teamId);
    }

    @Override
    public void switchToLoginView() {
        loggedInPresenter.switchToLoginView();
    }
}

