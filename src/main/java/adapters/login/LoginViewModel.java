package adapters.login;

import adapters.ViewModel;

/**
 * The view model for login functionality.
 */
public class LoginViewModel extends ViewModel<LoginState> {
    public LoginViewModel() {
        super("login");
        setState(new LoginState());
    }
}
