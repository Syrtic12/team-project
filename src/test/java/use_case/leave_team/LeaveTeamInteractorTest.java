package use_case.leave_team;

import data_access.KandoMongoDatabase;
import data_access.LeaveTeamDataAccessObject;
import entity.Team;
import entity.TeamFactory;
import entity.TeamLeader;
import entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import use_case.leave_team.LeaveTeamDataAccessInterface;
import use_case.leave_team.LeaveTeamInputData;

import java.util.ArrayList;

public class LeaveTeamInteractorTest {
    KandoMongoDatabase kandoDB =new KandoMongoDatabase();
    LeaveTeamDataAccessInterface userRepository = new LeaveTeamDataAccessObject(kandoDB);

    @Test
    void successLeaveTeamTest() {

        TeamLeader leader = new TeamLeader("usecasetestman", "testfellow@gmail.com", "Leader", "pass");
        kandoDB.add(leader);
        String leaderId = leader.getIdx();

        Team team = new Team(leaderId);
        kandoDB.add(team);
        String teamId = team.getIdx();

        User user = new User("leaveman", "leaver@mail.com", "Member", "123");
        kandoDB.add(user);
        String userId = user.getIdx();

        ArrayList<String> thing = new ArrayList<String>();
        thing.add(userId);

        kandoDB.update("teams", teamId, "users", thing);


        LeaveTeamOutputBoundary successPresenter = new LeaveTeamOutputBoundary() {

            @Override
            public void prepareSuccessView(LeaveTeamOutputData data) {

                Assertions.assertTrue(data.isSuccess());
                Assertions.assertEquals("Success", data.getMessage());
            }

            @Override
            public void prepareFailView(LeaveTeamOutputData outputData) {
                Assertions.fail("Unexpected failure: ");
            }
        };

        LeaveTeamInputData input = new LeaveTeamInputData(teamId, userId);
        LeaveTeamInteractor interactor = new LeaveTeamInteractor(userRepository, successPresenter);
        interactor.execute(input);

        System.out.println(userRepository.getTeamMembers(userRepository.getTeam(teamId)));
        Assertions.assertFalse(userRepository.getTeamMembers(userRepository.getTeam(teamId)).contains(userId));
    }
}
