package interface_adapter.signup;

import view.SignupView;

public class SignupViewModel {
    public static final String TITLE_LABEL = "Signup";
    public static final String USERNAME_LABEL = "Username";
    public static final String PASSWORD_LABEL = "Password";
    public static final String REPEAT_PASSWORD_LABEL = "Repeat Password";
    public static final String EMAIL_LABEL = "Email";
    public static final String TO_LOGIN_BUTTON_LABEL = "Login";
    public static final String SIGNUP_BUTTON_LABEL = "Signup";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    public void addPropertyChangeListener(SignupView signupView) {
        /*
        I dunno what to do here
         */
    }

    public SignupState getState() {
        return new SignupState();
    }

    public void setState(SignupState currentState) {
        /*
        I dunno what to do here
         */
    }
}
