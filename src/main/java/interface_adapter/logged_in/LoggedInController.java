
package interface_adapter.logged_in;

import use_case.logged_in.LoggedInInputBoundary;
import use_case.logged_in.LoggedInInputData;


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
