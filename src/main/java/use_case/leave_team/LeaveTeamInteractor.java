package use_case.leave_team;

import entity.Team;
import entity.User;
import view.ManageTeamView;

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
        if (!dataAccessObject.getTeamMembers(team).contains(leaveTeamInputData.getUserId())) {
            leaveTeamPresenter.prepareFailView("User is not in the team.");
        }
        if (Objects.equals(dataAccessObject.getTeamLeader(team).getIdx(), user.getIdx())) {
            leaveTeamPresenter.prepareFailView("Team leader cannot leave the team.");
        }



    }
}
