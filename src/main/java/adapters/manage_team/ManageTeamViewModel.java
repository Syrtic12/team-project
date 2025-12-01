package adapters.manage_team;

import adapters.ViewModel;

public class ManageTeamViewModel extends ViewModel<ManageTeamState>{
    public static final String TITLE_LABEL = "Manage Team";
    public static final String ADD_MEMBER_LABEL = "Add Member";
    public static final String MEMBERS_LABEL = "Team Members";
    public static final String ADD_BUTTON_LABEL = "Add";
    public static final String REMOVE_BUTTON_LABEL = "Remove";
    public static final String BACK_BUTTON_LABEL = "Back";
    public static final String DISBAND_BUTTON_LABEL = "Disband Team";

    public ManageTeamViewModel() {
        super("manage team");
        setState(new ManageTeamState());
    }
}
