package use_case.signup;

/**
 * The output boundary for the Signup Use Case.
 */
public interface SignUpOutputBoundary {

    /**
     * Prepares the success view for the SignUp Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(SignUpOutputData outputData);

    /**
     * Prepares the failure view for the Signup Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches to the Login View.
     */
    void switchToLoginView();

}