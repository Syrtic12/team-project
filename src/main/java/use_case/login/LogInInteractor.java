package use_case.login;

import data_access.KandoMongoDatabase;
import data_access.LogInDataAccessObject;
import entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Interactor for the LogIn use case.
 */
public class    LogInInteractor implements LogInInputBoundary {
    private final LogInDataAccessInterface dataAccessObject;
    private final LogInOutputBoundary loginPresenter;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public LogInInteractor(LogInDataAccessInterface dataAccessInterface,
                           LogInOutputBoundary loginOutputBoundary) {
        this.dataAccessObject = dataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    @Override
    public void execute(LogInInputData loginInputData) {
        String email = loginInputData.getEmail();
        String password = loginInputData.getPassword();
        if (email.isEmpty() || password.isEmpty()) {
            loginPresenter.prepareFailView("Email or password cannot be empty.");
        } else if (!(dataAccessObject.emailExists(email))){
            loginPresenter.prepareFailView("Account does not exist.");
        } else {
            User user = dataAccessObject.getUser(email);
            if (encoder.matches(password, user.getPassword())) {
                LogInOutputData outputData = new LogInOutputData(user.getIdx(), user.getEmail(), user.getTeams());
                loginPresenter.prepareSuccessView(outputData);
            } else {
                loginPresenter.prepareFailView("Incorrect password.");
            }
        }
    }

    public void switchToSignupView(){
        loginPresenter.switchToSignupView();
    }

    public static void main(String[] args) {
        LogInInteractor interac = new LogInInteractor(new LogInDataAccessObject(new KandoMongoDatabase()), null);
        interac.execute(new LogInInputData("sushaanpatel@gmail.com","password"));
    }
}
