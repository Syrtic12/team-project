package adapters.login;

import adapters.ViewModel;

public class LoginViewModel extends ViewModel<LoginState>{
    public LoginViewModel() {
        super("login");
        setState(new LoginState());
    }
}
