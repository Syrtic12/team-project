package use_case.logged_in;

import use_case.login.LogInInputData;

public interface LoggedInInputBoundary {

    /**
     * Executes the login use case.
     * @param loggedinInputData the input data
     */
    void execute(LoggedInInputData loggedinInputData);

    void switchToTeamView();

    void switchToLoginView();
}
