package interface_adapter.login;

import use_case.login.LogInInputBoundary;
import use_case.login.LogInInputData;

public class LoginController {

    private final LogInInputBoundary loginInteractor;
    public LoginController(LogInInputBoundary loginInteractor) {
        this.loginInteractor = loginInteractor;
    }

    public void execute(String email, String password) {
        final LogInInputData logInInputData = new LogInInputData(email, password);
    this.loginInteractor.execute(logInInputData);
    }
}
