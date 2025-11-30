package use_case.signup;

/**
 * Input Boundary for the SignUp use case.
 */
public interface SignUpInputBoundary {

    /**
     * Executes the signup use case.
     * @param signupInputData the input data
     */
    void execute(LeaveTeamInputData signupInputData);

    /**
     * Executes the switch to login view use case.
     */
    void switchToLoginView();
}