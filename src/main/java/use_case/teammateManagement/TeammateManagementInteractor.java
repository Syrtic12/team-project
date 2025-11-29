package use_case.teammateManagement;

import entity.User;
import entity.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Interactor for the LogIn use case.
*/
public class TeammateManagementInteractor implements TeammateManagementInputBoundary {
    private final TeammateManagementDataAccessInterface dataAccessObject;
    private final TeammateManagementOutputBoundary teammateManagementPresenter;

    public TeammateManagementInteractor(TeammateManagementDataAccessInterface dataAccessObject, TeammateManagementOutputBoundary teammateManagementPresenter) {
        this.dataAccessObject = dataAccessObject;
        this.teammateManagementPresenter = teammateManagementPresenter;
    }

    @Override
    public void execute(TeammateManagementInputData TeammateManagementInputData) {
        User user = null;
        try {
            user = dataAccessObject.getUser(TeammateManagementInputData.getEmail());
        }
        catch (NullPointerException e) {
            user = null;
        }
        Team team = dataAccessObject.getTeam(TeammateManagementInputData.getTeamID());
        String action = TeammateManagementInputData.getAction();
        //Checks if team or user exists
        if ((team == null) || (user == null)) {
            teammateManagementPresenter.prepareFailView("Team/User not found.");
            return;
        } else if (action.isEmpty()){
            teammateManagementPresenter.prepareFailView("No action has been specified.");
            return;
        } else if ((Objects.equals(dataAccessObject.getTeamLeader(team).getIdx(), user.getIdx()))&&(action.equals("remove"))) {
            teammateManagementPresenter.prepareFailView("You cannot remove yourself.");
            return;
        } else if ((!dataAccessObject.getTeamMembers(team).contains(user.getIdx()) && (action.equals("remove")))) {
            teammateManagementPresenter.prepareFailView("This User is not in this team");
            return;
        } else {
            boolean result = false;
            if (action.equals("add")){
                result = (this.dataAccessObject.addUser(team, user)) && (this.dataAccessObject.addTeam(team, user));
            }
            else if (action.equals("remove")){
                result = (this.dataAccessObject.removeUser(team, user)) &&
                        (this.dataAccessObject.removeTeam(team, user));
            }
            if (!result){
                teammateManagementPresenter.prepareFailView("An error has occurred");
            }
            List<String> teamMembers = dataAccessObject.getTeamMembers(team);
            List<String> teamEmails = new ArrayList<>();
            for (String userId : teamMembers){
                User member = dataAccessObject.getUserFromId(userId);
                teamEmails.add(member.getEmail());
            }
            TeammateManagementOutputData outputData = new TeammateManagementOutputData(true, team.getIdx(),
                    user.getIdx(), teamEmails);
            teammateManagementPresenter.prepareSuccessView(outputData);
        }
    }

    public void switchToTeamView() { teammateManagementPresenter.switchToTeamView();}

}



