package interface_adapter.logged_in;

import interface_adapter.ViewModel;
import  java.util.List;

public class LoggedInViewModel extends ViewModel<LoggedInState>{

    public LoggedInViewModel(){
        super("logged in");
        setState(new LoggedInState());
    }

    public void setEmail(String email) {
        LoggedInState state = getState();
        state.setEmail(email);
        firePropertyChange();
    }

    public void setTeams(List<String> teams) {
        LoggedInState state = getState();
        state.setTeams(teams);
        firePropertyChange();
    }

    public void addTeam(String teamName) {
        LoggedInState state = getState();
        state.addTeam(teamName);
        firePropertyChange();
    }
}
