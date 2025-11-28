package use_case.teammateManagement;

import entity.User;
import entity.Team;
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
        User user = dataAccessObject.getUser(TeammateManagementInputData.getUserID());
        Team team = dataAccessObject.getTeam(TeammateManagementInputData.getTeamID());
        String action = TeammateManagementInputData.getAction();
        //Checks if team or user exists
        if ((team.getIdx() == null) || (user.getIdx() == null)) {
            teammateManagementPresenter.prepareFailView("Team/User not found.");
            return;
        } else if (action.isEmpty()){
            teammateManagementPresenter.prepareFailView("No action has been specified.");
            return;
        } else if ((Objects.equals(dataAccessObject.getTeamLeader(team).getIdx(), user.getIdx()))&&(action.equals("remove"))) {
            teammateManagementPresenter.prepareFailView("You cannot "+ action+ "remove yourself.");
            return;
        } else if ((!dataAccessObject.getTeamMembers(team).contains(user.getIdx()) && (action.equals("remove")))) {
            teammateManagementPresenter.prepareFailView("This User is not in this team");
            return;
        } else {
            boolean result = false;
            if (action.equals("add")){
                result = this.dataAccessObject.addUser(team, user);
            }
            else if (action.equals("remove")){
                result = this.dataAccessObject.removeUser(team, user);
            }
            if (!result){
                teammateManagementPresenter.prepareFailView("An error has occured");
            }
            TeammateManagementOutputData outputData = new TeammateManagementOutputData(true, team.getIdx(), user.getIdx(), "Success");
            teammateManagementPresenter.prepareSuccessView(outputData);
        }
        }

}



