package adapters.logged_in;

import usecase.logged_in.LoggedInInputBoundary;


public class LoggedInController {
    private final LoggedInInputBoundary loggedInInteractor;
    public LoggedInController(LoggedInInputBoundary loggedInInteractor) {
        this.loggedInInteractor = loggedInInteractor;
    }

    public void createTeam(String teamName) {

    }

    public void logout() {
        loggedInInteractor.switchToLoginView();
    }

    public void switchToTeamView(String teamId) {
        loggedInInteractor.switchToTeamView(teamId);
    }
}
