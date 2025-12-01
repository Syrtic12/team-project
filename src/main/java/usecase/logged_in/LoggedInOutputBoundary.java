package usecase.logged_in;

public interface LoggedInOutputBoundary {
    void prepareSuccessView(LoggedInOutputData outputData);

    /**
     * Prepares the failure view.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    void switchToTeamView(LoggedInOutputData outputData);
    void switchToLoginView();

}
