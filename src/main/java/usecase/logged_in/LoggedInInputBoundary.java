package usecase.logged_in;

public interface LoggedInInputBoundary {

    /**
     * Executes the login use case.
     * @param loggedinInputData the input data
     */
    void execute(LoggedInInputData loggedinInputData);

    void switchToTeamView(String teamId);

    void switchToLoginView();
}
