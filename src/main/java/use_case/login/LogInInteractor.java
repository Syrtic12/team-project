package use_case.login;

import data_access.KandoMongoDatabase;
import data_access.LogInDataAccessObject;
import entity.User;

/**
 * Interactor for the LogIn use case.
 */
public class LogInInteractor implements LogInInputBoundary {
    private final LogInDataAccessInterface dataAccessObject;
    private final LogInOutputBoundary loginPresenter;

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
            loginPresenter.prepareFailView("Email and password cannot be empty.");
        } else if (!(dataAccessObject.emailExists(email))){
            loginPresenter.prepareFailView("Account does not exist.");
        } else {
            User user = dataAccessObject.getUser(email);
            if (user.getPassword().equals(password)) {
                LogInOutputData outputData = new LogInOutputData(user.getIdx(), user.getEmail());
                loginPresenter.prepareSuccessView(outputData);
            } else {
                loginPresenter.prepareFailView("Incorrect password.");
            }
        }
    }

    public static void main(String[] args) {
        LogInInteractor interac = new LogInInteractor(new LogInDataAccessObject(new KandoMongoDatabase()), null);
        interac.execute(new LogInInputData("sushaanpatel@gmail.com","password"));
    }
}
