package usecase.signup;

import entity.User;
import entity.UserFactory;

/**
 * The SignUpInteractor class implements the SignUpInputBoundary interface and handles the user sign-up process.
 */
public class SignUpInteractor implements SignUpInputBoundary {
    private final SignUpDataAccessInterface userDataAccessObject;
    private final SignUpOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public SignUpInteractor(SignUpDataAccessInterface userDataAccessObject,
                            SignUpOutputBoundary userPresenter,
                            UserFactory userFactory) {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(SignUpInputData signupInputData) {
        final String name = signupInputData.getName();
        final String email = signupInputData.getEmail();
        final String password = signupInputData.getPassword();
        final String repeatPassword = signupInputData.getRepeatPassword();
        if (userDataAccessObject.emailExists(signupInputData.getEmail())) {
            userPresenter.prepareFailView("Email already exists");
        }
        else if (password.isEmpty() || repeatPassword.isEmpty()) {
            userPresenter.prepareFailView("Passwords cannot be empty");
        }
        else if (!password.equals(repeatPassword)) {
            userPresenter.prepareFailView("Passwords do not match");
        }
        else if (name.isEmpty()) {
            userPresenter.prepareFailView("Name cannot be empty");
        }
        else if (email.isEmpty()) {
            userPresenter.prepareFailView("Email cannot be empty");
        }
        else {
            final User newUser = userFactory.create(name, email, password, "");
            final User addedUser = userDataAccessObject.addUser(newUser);
            final SignUpOutputData signupOutputData = new SignUpOutputData(addedUser.getIdx(), addedUser.getEmail());
            userPresenter.prepareSuccessView(signupOutputData);
        }
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }
}
