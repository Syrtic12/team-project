package usecase.login;

import entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Interactor for the LogIn use case.
 */
public class LogInInteractor implements LogInInputBoundary {
    private final LogInDataAccessInterface dataAccessObject;
    private final LogInOutputBoundary loginPresenter;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public LogInInteractor(LogInDataAccessInterface dataAccessInterface,
                           LogInOutputBoundary loginOutputBoundary) {
        this.dataAccessObject = dataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    @Override
    public void execute(LogInInputData loginInputData) {
        final String email = loginInputData.getEmail();
        final String password = loginInputData.getPassword();
        if (email.isEmpty() || password.isEmpty()) {
            loginPresenter.prepareFailView("Email or password cannot be empty.");
        }
        else if (!(dataAccessObject.emailExists(email))) {
            loginPresenter.prepareFailView("Account does not exist.");
        }
        else {
            final User user = dataAccessObject.getUser(email);
            if (encoder.matches(password, user.getPassword())) {
                final LogInOutputData outputData = new LogInOutputData(user.getIdx(), user.getEmail(),
                        dataAccessObject.getTeams(user.getIdx()));
                System.out.println("DEBUG LogInInteractor: user.getIdx() = " + user.getIdx());
                loginPresenter.prepareSuccessView(outputData);
            }
            else {
                loginPresenter.prepareFailView("Incorrect password.");
            }
        }
    }

    /**
     * Switches to the signup view.
     */
    public void switchToSignupView() {
        loginPresenter.switchToSignupView();
    }
}

