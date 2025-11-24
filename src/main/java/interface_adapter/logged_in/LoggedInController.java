package interface_adapter.logged_in;

import use_case.logged_in.LoggedInInputBoundary;

public class LoggedInController {
    private final LoggedInInputBoundary loggedInInputBoundary;
    public LoggedInController(LoggedInInputBoundary loggedInInputBoundary) {
        this.loggedInInputBoundary = loggedInInputBoundary;
    }

    public void createTeam(String teamName) {

    }

    public void logout() {

    }

    public void switchToTeamView(String teamId) {

    }
}
