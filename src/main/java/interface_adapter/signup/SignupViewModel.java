package interface_adapter.signup;

import interface_adapter.ViewModel;
import view.LoginView;
import view.SignupView;

public class SignupViewModel extends ViewModel<SignupState> {
    public static final String TITLE_LABEL = "Signup";
    public static final String USERNAME_LABEL = "Username";
    public static final String PASSWORD_LABEL = "Password";
    public static final String REPEAT_PASSWORD_LABEL = "Repeat Password";
    public static final String EMAIL_LABEL = "Email";
    public static final String TO_LOGIN_BUTTON_LABEL = "Login";
    public static final String SIGNUP_BUTTON_LABEL = "Signup";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";
    private SignupState signupState;

    public SignupViewModel(){
        super("sign up");
        setState(new SignupState());
    }

//    public SignupState getState() {
//        return this.signupState;
//    }
//
//    public void setState(SignupState currentState) {
//        this.signupState = currentState;
//    }
}
