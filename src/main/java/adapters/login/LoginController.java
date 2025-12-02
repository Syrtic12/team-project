package adapters.login;

import usecase.login.LogInInputBoundary;
import usecase.login.LogInInputData;

/**
 * The controller for handling login actions.
 */
public class LoginController {

    private final LogInInputBoundary loginInteractor;

    public LoginController(LogInInputBoundary loginInteractor) {
        this.loginInteractor = loginInteractor;
    }

    /**
     * Executes the login process with the provided email and password.
     * @param email email of the user
     * @param password password of the user
     */
    public void execute(String email, String password) {
        final LogInInputData logInInputData = new LogInInputData(email, password);
        loginInteractor.execute(logInInputData);
    }

    /**
     * Switches to the signup view.
     */
    public void switchToSignupView() {
        loginInteractor.switchToSignupView();
    }
}
