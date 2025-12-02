package usecase.leave_team;

import entity.Team;
import entity.User;

/**
 * Interactor responsible for handling the user's request to leave a team.
 * It retrieves the relevant team and user data, performs validation,
 * updates the team membership, and prepares either a success or fail view.
 */
public class LeaveTeamInteractor implements LeaveTeamInputBoundary {

    private final LeaveTeamDataAccessInterface dataAccessObject;
    private final LeaveTeamOutputBoundary leaveTeamPresenter;

    /**
     * Constructs a LeaveTeamInteractor with the required data access and output boundary.
     *
     * @param dataAccessObject  the data access interface used to retrieve and modify team data
     * @param leaveTeamPresenter the presenter responsible for preparing output views
     */
    public LeaveTeamInteractor(LeaveTeamDataAccessInterface dataAccessObject,
                               LeaveTeamOutputBoundary leaveTeamPresenter) {
        this.dataAccessObject = dataAccessObject;
        this.leaveTeamPresenter = leaveTeamPresenter;
    }

    /**
     * Executes the leave team use case using the given input data.
     * Retrieves the team and user, validates membership, removes the user if applicable,
     * and prepares the appropriate success or failure view.
     *
     * @param leaveTeamInputData the data required to process the leave team request
     */
    public void execute(LeaveTeamInputData leaveTeamInputData) {
        final Team team = dataAccessObject.getTeam(leaveTeamInputData.getTeamId());
        final User user = dataAccessObject.getUser(leaveTeamInputData.getUserId());
        if (team.getIdx() == null) {
            final LeaveTeamOutputData outputData = new LeaveTeamOutputData(false, null,
                    null, "Team/User not found.", dataAccessObject.getTeamMembers(team));
            leaveTeamPresenter.prepareFailView(outputData);
        }
        else if (user.getIdx() == null) {
            final LeaveTeamOutputData outputData = new LeaveTeamOutputData(false, null,
                    null, "Team/User not found.", dataAccessObject.getTeamMembers(team));
            leaveTeamPresenter.prepareFailView(outputData);
        }
        else {
            if (!dataAccessObject.getTeamMembers(team).contains(leaveTeamInputData.getUserId())) {
                final LeaveTeamOutputData outputData = new LeaveTeamOutputData(false, team.getIdx(),
                        user.getIdx(), "User is not in the team", dataAccessObject.getTeamMembers(team));
                leaveTeamPresenter.prepareFailView(outputData);
            }
            else {
                final boolean success = dataAccessObject.removeMember(team.getIdx(), user.getIdx());
                if (!success) {
                    final LeaveTeamOutputData outputData = new LeaveTeamOutputData(false, team.getIdx(),
                            user.getIdx(), "An error has occurred", dataAccessObject.getTeamMembers(team));
                    leaveTeamPresenter.prepareFailView(outputData);
                }
                final LeaveTeamOutputData outputData = new LeaveTeamOutputData(success, team.getIdx(), user.getIdx(),
                        "Success", dataAccessObject.getTeamMembers(team));
                leaveTeamPresenter.prepareSuccessView(outputData);

            }
        }
    }
}
