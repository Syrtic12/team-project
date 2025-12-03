package adapters.leave_team;

import usecase.leave_team.LeaveTeamInputBoundary;
import usecase.leave_team.LeaveTeamInputData;

/**
 * Controller for the Leave Team use case.
 * This class receives user input (team ID and user ID) from the UI layer
 * and forwards it to the LeaveTeamInputBoundary as a LeaveTeamInputData object.
 */
public class LeaveTeamController {

    private final LeaveTeamInputBoundary interactor;

    /**
     * Constructs a LeaveTeamController with the given interactor.
     *
     * @param interactor the input boundary that handles the leave team use case
     */
    public LeaveTeamController(LeaveTeamInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the leave team operation for the specified user and team.
     *
     * @param teamId the ID of the team the user is leaving
     * @param userId the ID of the user requesting to leave the team
     */
    public void execute(String teamId, String userId) {
        interactor.execute(new LeaveTeamInputData(teamId, userId));
    }
}
