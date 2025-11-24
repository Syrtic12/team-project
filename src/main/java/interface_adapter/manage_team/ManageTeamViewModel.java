package interface_adapter.manage_team;

import interface_adapter.ViewModel;

public class ManageTeamViewModel extends ViewModel<ManageTeamState>{
    public ManageTeamViewModel() {
        super("Manage Team");
        setState(new ManageTeamState());
    }
}
