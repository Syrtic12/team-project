package interface_adapter.signup;

import use_case.signup.SignUpInputBoundary;
import use_case.signup.SignUpInputData;
import use_case.signup.SignUpInteractor;

public class SignupController {
    private final SignUpInputBoundary SignUpInteractor;
    public SignupController(SignUpInputBoundary SignUpInteractor) {
        this.SignUpInteractor = SignUpInteractor;
    }
    public void execute(String username, String password, String repeatPassword, String email, String role) {
        final SignUpInputData signUpInputData = new SignUpInputData(email, username, role, password, repeatPassword );
        SignUpInteractor.execute(signUpInputData);
    }

    public void switchToLoginView() {
        SignUpInteractor.switchToLoginView();
    }
}
