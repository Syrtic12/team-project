
package interface_adapter.team;

import interface_adapter.ViewModel;
import interface_adapter.signup.SignupState;

public class TeamViewModel extends ViewModel<TeamState> {
    public TeamViewModel() {
        super("team view");
        setState(new TeamState());
    }
}
