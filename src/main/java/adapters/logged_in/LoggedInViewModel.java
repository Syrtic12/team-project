package adapters.logged_in;

import adapters.ViewModel;
public class LoggedInViewModel extends ViewModel<LoggedInState> {
    public LoggedInViewModel(LoggedInState loggedInState) {
        super("logged in");
        setState(loggedInState);
    }

}
