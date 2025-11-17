package interface_adapter.login;

import interface_adapter.ViewModel;
import view.LoginView;

public class LoginViewModel extends ViewModel<LoginState>{
    public LoginViewModel() {
        super("login");
        setState(new LoginState());
    }

    public void addPropertyChangeListener(LoginView loginView) {
        /*
        I dunno what to do here
         */
    }
}
