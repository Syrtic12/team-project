package interface_adapter.login;

import interface_adapter.ViewModel;
import view.LoginView;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LoginViewModel extends ViewModel<LoginState>{
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public LoginViewModel() {
        super("login");
        setState(new LoginState());
    }

    public void addPropertyChangeListener(LoginView loginView) {
        this.support.addPropertyChangeListener((PropertyChangeListener) loginView);
    }

    @Override
    public void setState(LoginState state) {
        super.setState(state);
        this.support.firePropertyChange("state", null, state);
    }
}
