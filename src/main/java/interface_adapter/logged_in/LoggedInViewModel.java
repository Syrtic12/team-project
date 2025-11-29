package interface_adapter.logged_in;

import interface_adapter.ViewModel;
import  java.util.List;

public class LoggedInViewModel extends ViewModel<LoggedInState>{

    public LoggedInViewModel(){
        super("logged in");
        setState(new LoggedInState());
    }

}
