package interface_adapter.signup;

import use_case.signup.SignUpInputBoundary;
import use_case.signup.SignUpInputData;

public class SignupController {
    private final SignUpInputBoundary SignUpInteractor;
    public SignupController(SignUpInputBoundary SignUpInteractor) {
        this.SignUpInteractor = SignUpInteractor;
    }
    public void execute(String email, String username, String password, String repeatPassword) {
        final SignUpInputData signUpInputData = new SignUpInputData(email, username, password, repeatPassword);
        SignUpInteractor.execute(signUpInputData);
    }

    public void switchToLoginView() {
        SignUpInteractor.switchToLoginView();
    }
}
