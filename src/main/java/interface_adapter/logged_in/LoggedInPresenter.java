package interface_adapter.logged_in;
import entity.Task;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.team.TeamState;
import interface_adapter.team.TeamViewModel;
import use_case.logged_in.LoggedInOutputBoundary;
import use_case.logged_in.LoggedInOutputData;
import use_case.login.LogInOutputBoundary;
import use_case.login.LogInOutputData;
import data_access.LoggedInDataAccessObject;
import data_access.KandoMongoDatabase;


public class LoggedInPresenter implements LoggedInOutputBoundary {
    private final LoggedInViewModel loggedInViewModel;
    private final LoggedInState loggedInState;
    private final ViewManagerModel viewManagerModel;
    private final TeamViewModel teamViewModel;
    private final LoginViewModel loginViewModel;
    private final LoggedInDataAccessObject loggedInDataAccessObject;
    public LoggedInPresenter(LoggedInViewModel loggedInViewModel, LoggedInState loggedInState,
                             ViewManagerModel viewManagerModel, TeamViewModel teamViewModel,
                             LoginViewModel loginViewModel,  LoggedInDataAccessObject loggedInDataAccessObject) {
        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
        this.teamViewModel = teamViewModel;
        this.loggedInState = loggedInState;
        this.loginViewModel = loginViewModel;
        this.loggedInDataAccessObject = loggedInDataAccessObject;
    }


    @Override
    public void prepareSuccessView(LoggedInOutputData outputData) {
        final TeamState teamState = teamViewModel.getState();
        //Step 1: Use ID to get task data from DB
        //Step 2: Get Task title and status
        //Step 3: filter by status into map, adding the ID and Title in that order
        //Step 4: Loop repeats for each task in LoggedInOutputData tasklist
        for (Task task:outputData.getTaskList()){
            String taskID = "";
            int status = 0;

        }
    }

    @Override
    public void prepareFailView(String errorMessage) {

    }

    @Override
    public void switchToTeamView() {
        viewManagerModel.setState(teamViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToLoginView() {
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
