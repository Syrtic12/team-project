package use_case.create_task;

import data_access.KandoMongoDatabase;
import data_access.TaskDataAccessObject;
import entity.Task;
import entity.Team;
import entity.TeamLeader;
import entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * Integration tests for CreateTaskInteractor using real MongoDB.
 * Same structure as DeleteTaskInteractorTest.
 */
class CreateTaskInteractorTest {

    private final KandoMongoDatabase kandoDB = new KandoMongoDatabase();
    private final TaskDataAccessObject dao = new TaskDataAccessObject(kandoDB);

    // ------------------------------------------------------------
    // SUCCESS CASE
    // ------------------------------------------------------------
    @Test
    void successCreateTask() {
        String leaderId;
        String teamId;

        // Leader
        TeamLeader leader = new TeamLeader("Leader Name", "leader@mail.com", "Leader", "pw");
        kandoDB.add(leader);
        leaderId = leader.getIdx();

        // Team
        Team team = new Team(leader);
        kandoDB.add(team);
        teamId = team.getIdx();

        // Presenter
        CreateTaskOutputBoundary presenter = new CreateTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateTaskOutputData data) {
                Assertions.assertTrue(data.isSuccess());
                Assertions.assertNotNull(data.getTaskIdx());
                Assertions.assertEquals("Task created", data.getMessage());
            }

            @Override
            public void prepareFailView(CreateTaskOutputData data) {
                Assertions.fail("Unexpected failure: " + data.getMessage());
            }
        };

        CreateTaskInteractor interactor = new CreateTaskInteractor(dao, presenter);

        // Your CreateTaskInputData signature is:
        // (teamId, invokedBy, status, description, title)
        CreateTaskInputData input = new CreateTaskInputData(
                teamId, leaderId,
                0,
                "Task Description",
                "Task Title"
        );

        interactor.execute(input);

        // Verify the task exists + team has it attached
        Optional<Team> refreshed = dao.getTeam(teamId);
        Assertions.assertTrue(refreshed.isPresent());

        boolean found = false;
        for (String tid : refreshed.get().getTasks()) {
            Optional<Task> t = dao.getTask(tid);
            if (t.isPresent() && t.get().getTitle().equals("Task Title")) {
                found = true;
                break;
            }
        }

        Assertions.assertTrue(found, "Newly created task should appear in team's task list");
    }

    // ------------------------------------------------------------
    // FAIL: NOT LEADER
    // ------------------------------------------------------------
    @Test
    void failNotLeader() {
        String leaderId;
        String memberId;
        String teamId;

        // Leader
        TeamLeader leader = new TeamLeader("Leader Name", "leader@mail.com", "Leader", "pw");
        kandoDB.add(leader);
        leaderId = leader.getIdx();

        // Team
        Team team = new Team(leader);
        kandoDB.add(team);
        teamId = team.getIdx();

        // Non-leader Member
        User member = new User("Bob", "bob@mail.com", "Member", "pw");
        kandoDB.add(member);
        memberId = member.getIdx();

        CreateTaskOutputBoundary presenter = new CreateTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateTaskOutputData data) {
                Assertions.fail("Should NOT succeed when non-leader attempts to create a task");
            }

            @Override
            public void prepareFailView(CreateTaskOutputData data) {
                Assertions.assertEquals("Only team leader can create tasks", data.getMessage());
            }
        };

        CreateTaskInteractor interactor = new CreateTaskInteractor(dao, presenter);

        CreateTaskInputData input = new CreateTaskInputData(
                teamId, memberId,
                0,
                "Desc",
                "Title"
        );

        interactor.execute(input);
    }

    // ------------------------------------------------------------
    // FAIL: TEAM NOT FOUND
    // ------------------------------------------------------------
    @Test
    void failTeamNotFound() {
        String leaderId;

        // Real leader
        TeamLeader leader = new TeamLeader("Leader", "leader@mail.com", "Leader", "pw");
        kandoDB.add(leader);
        leaderId = leader.getIdx();

        CreateTaskOutputBoundary presenter = new CreateTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateTaskOutputData data) {
                Assertions.fail("Team does not exist — should NOT succeed");
            }

            @Override
            public void prepareFailView(CreateTaskOutputData data) {
                Assertions.assertEquals("Team not found", data.getMessage());
            }
        };

        CreateTaskInteractor interactor = new CreateTaskInteractor(dao, presenter);

        CreateTaskInputData input = new CreateTaskInputData(
                "000000000000000000000000",
                leaderId,
                0,
                "Desc",
                "Title"
        );

        interactor.execute(input);
    }

    // ------------------------------------------------------------
    // FAIL: USER NOT FOUND
    // ------------------------------------------------------------
    @Test
    void failUserNotFound() {
        String teamId;

        // Team Leader
        TeamLeader leader = new TeamLeader("Leader", "leader@mail.com", "Leader", "pw");
        kandoDB.add(leader);

        // Team
        Team team = new Team(leader);
        kandoDB.add(team);
        teamId = team.getIdx();

        CreateTaskOutputBoundary presenter = new CreateTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateTaskOutputData data) {
                Assertions.fail("User does not exist — should NOT succeed");
            }

            @Override
            public void prepareFailView(CreateTaskOutputData data) {
                Assertions.assertEquals("User not found", data.getMessage());
            }
        };

        CreateTaskInteractor interactor = new CreateTaskInteractor(dao, presenter);

        // nonexistent user ID
        CreateTaskInputData input = new CreateTaskInputData(
                teamId,
                "000000000000000000000000",
                0,
                "Desc",
                "Title"
        );

        interactor.execute(input);
    }
}
