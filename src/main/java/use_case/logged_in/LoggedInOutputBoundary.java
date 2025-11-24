package use_case.logged_in;

import use_case.login.LogInOutputData;

public interface LoggedInOutputBoundary {
    void prepareSuccessView(LoggedInOutputData outputData);

    /**
     * Prepares the failure view.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    void switchToTaskView();
    void switchToLoginView();

}
