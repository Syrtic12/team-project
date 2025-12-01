package adapters.logged_in;
import adapters.ViewManagerModel;
import adapters.login.LoginViewModel;
import adapters.team.TeamState;
import adapters.team.TeamViewModel;
import usecase.logged_in.LoggedInOutputBoundary;
import usecase.logged_in.LoggedInOutputData;
import dataaccess.LoggedInDataAccessObject;

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
        teamState.setUserId(outputData.getUserId());
        teamState.setTeamMembers(outputData.getTeamMembers());
        teamState.setLeaderId(outputData.getLeaderId());
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
