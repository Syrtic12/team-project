package adapters.signup;

import usecase.signup.SignUpInputBoundary;
import usecase.signup.SignUpInputData;

/**
 * The controller for handling signup actions.
 */
public class SignupController {
    private final SignUpInputBoundary signUpInteractor;

    public SignupController(SignUpInputBoundary signUpInteractor) {
        this.signUpInteractor = signUpInteractor;
    }

    /**
     * Executes the signup process with the provided email, username, password, and repeated password.
     * @param email email of the user
     * @param username username of the user
     * @param password password of the user
     * @param repeatPassword repeated password for confirmation
     */
    public void execute(String email, String username, String password, String repeatPassword) {
        final SignUpInputData signUpInputData = new SignUpInputData(email, username, password, repeatPassword);
        signUpInteractor.execute(signUpInputData);
    }

    /**
     * Switches to the login view.
     */
    public void switchToLoginView() {
        signUpInteractor.switchToLoginView();
    }
}
