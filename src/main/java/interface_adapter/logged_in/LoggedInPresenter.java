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
import java.util.Map;

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

    }

    @Override
    public void prepareFailView(String errorMessage) {

    }

    @Override
    public void switchToTeamView(LoggedInOutputData outputData) {
        final TeamState teamState = teamViewModel.getState();
        teamState.setNotStartedTasks(outputData.getNotStartedTasks());
        teamState.setInProgressTasks(outputData.getInProgressTasks());
        teamState.setCompletedTasks(outputData.getCompletedTasks());
        teamState.setTeamName(outputData.getTeamId());
        this.teamViewModel.firePropertyChange();
        this.viewManagerModel.setState(teamViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToLoginView() {
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
