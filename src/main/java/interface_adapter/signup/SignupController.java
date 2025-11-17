package interface_adapter.signup;

import use_case.signup.SignUpInputBoundary;
import use_case.signup.SignUpInputData;
import use_case.signup.SignUpInteractor;

public class SignupController {
    private final SignUpInputBoundary SignUpInteractor;
    public SignupController(SignUpInputBoundary SignUpInteractor) {
        this.SignUpInteractor = SignUpInteractor;
    }
    public void execute(String username, String password, String repeatPassword, String email) {
        final SignUpInputData signUpInputData = new SignUpInputData(email, username, password, repeatPassword );
        SignUpInteractor.execute(signUpInputData);
    }

    public void switchToLoginView() {
        SignUpInteractor.switchToLoginView();
    }
}
