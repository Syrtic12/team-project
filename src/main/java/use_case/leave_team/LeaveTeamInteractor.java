package use_case.leave_team;

import entity.Team;
import entity.User;
import java.util.Objects;

public class LeaveTeamInteractor implements LeaveTeamInputBoundary{

    private final LeaveTeamDataAccessInterface dataAccessObject;
    private final LeaveTeamOutputBoundary leaveTeamPresenter;

    public LeaveTeamInteractor(LeaveTeamDataAccessInterface dataAccessObject, LeaveTeamOutputBoundary leaveTeamPresenter) {
        this.dataAccessObject = dataAccessObject;
        this.leaveTeamPresenter = leaveTeamPresenter;
    }

    public void execute(LeaveTeamInputData leaveTeamInputData) {
        Team team = dataAccessObject.getTeam(leaveTeamInputData.getTeamId());
        User user = dataAccessObject.getUser(leaveTeamInputData.getUserId());
        if ((team.getIdx() == null) || (user.getIdx() == null)) {
            LeaveTeamOutputData outputData = new LeaveTeamOutputData(false, null, null, "Team/User not found.");
            leaveTeamPresenter.prepareFailView(outputData);
            return;
        }
        if (!dataAccessObject.getTeamMembers(team).contains(leaveTeamInputData.getUserId())) {
            LeaveTeamOutputData outputData = new LeaveTeamOutputData(false, team.getIdx(), user.getIdx(), "User is not in the team");
            leaveTeamPresenter.prepareFailView(outputData);
        }
        else if (Objects.equals(dataAccessObject.getTeamLeader(team).getIdx(), user.getIdx())) {
            LeaveTeamOutputData outputData = new LeaveTeamOutputData(false, team.getIdx(), user.getIdx(), "Team leader cannot leave the team");
            leaveTeamPresenter.prepareFailView(outputData);
        }
        else {
            boolean success = dataAccessObject.removeMember(team, user);
            if (!success) {
                LeaveTeamOutputData outputData = new LeaveTeamOutputData(false, team.getIdx(), user.getIdx(), "An error has occured");
                leaveTeamPresenter.prepareFailView(outputData);
            }
            LeaveTeamOutputData outputData = new LeaveTeamOutputData(success, team.getIdx(), user.getIdx(), "Success");
            leaveTeamPresenter.prepareSuccessView(outputData);

        }
    }
}
