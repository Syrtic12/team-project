package interface_adapter.logged_in;

import interface_adapter.ViewModel;
public class LoggedInViewModel extends ViewModel<LoggedInState> {
    public LoggedInViewModel(LoggedInState loggedInState) {
        super("logged in");
        setState(loggedInState);
    }

}
