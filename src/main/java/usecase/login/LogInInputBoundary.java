package usecase.login;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface LogInInputBoundary {

    /**
     * Executes the login use case.
     * @param loginInputData the input data
     */
    void execute(LogInInputData loginInputData);

    void switchToSignupView();
}