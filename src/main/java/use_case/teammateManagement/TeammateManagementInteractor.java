package use_case.teammateManagement;

import data_access.KandoMongoDatabase;
import data_access.TeammateManagementDataDataAccessObject;
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
        if ((team.getIdx() == null) || (user.getIdx() == null)) {
            TeammateManagementOutputData outputData = new TeammateManagementOutputData(false, null, null, "Team/User not found.");
            teammateManagementPresenter.prepareFailView(outputData);
            return;
        } else if (action.isEmpty()){
            TeammateManagementOutputData outputData = new TeammateManagementOutputData(false, null, null, "No action has been specified.");
            teammateManagementPresenter.prepareFailView(outputData);
            return;
        } else if (Objects.equals(dataAccessObject.getTeamLeader(team).getIdx(), user.getIdx())) {
            TeammateManagementOutputData outputData = new TeammateManagementOutputData(false, team.getIdx(), user.getIdx(), "You cannot "+ action+ "remove yourself.");
            teammateManagementPresenter.prepareFailView(outputData);
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
                TeammateManagementOutputData outputData = new TeammateManagementOutputData(false, team.getIdx(), user.getIdx(), "An error has occured");
                teammateManagementPresenter.prepareFailView(outputData);
            }
            TeammateManagementOutputData outputData = new TeammateManagementOutputData(true, team.getIdx(), user.getIdx(), "Success");
            teammateManagementPresenter.prepareSuccessView(outputData);
        }
        }
    }

