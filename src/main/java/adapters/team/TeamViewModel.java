
package adapters.team;

import adapters.ViewModel;

public class TeamViewModel extends ViewModel<TeamState> {
    public TeamViewModel() {
        super("team view");
        setState(new TeamState());
    }
}
