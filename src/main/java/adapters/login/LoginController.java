package adapters.login;

import usecase.login.LogInInputBoundary;
import usecase.login.LogInInputData;

public class LoginController {

    private final LogInInputBoundary loginInteractor;
    public LoginController(LogInInputBoundary loginInteractor) {
        this.loginInteractor = loginInteractor;
    }

    public void execute(String email, String password) {
        final LogInInputData logInInputData = new LogInInputData(email, password);
        loginInteractor.execute(logInInputData);
    }

    public void switchToSignupView() {
        loginInteractor.switchToSignupView();
    }
}
