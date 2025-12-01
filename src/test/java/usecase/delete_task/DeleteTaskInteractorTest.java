package usecase.delete_task;

import dataaccess.KandoMongoDatabase;
import dataaccess.TaskDataAccessObject;
import entity.Task;
import entity.Team;
import entity.TeamLeader;
import entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * Integration tests for DeleteTaskInteractor using real MongoDB.
 * No helper cleanup methods — cleanup removed as requested.
 */
class DeleteTaskInteractorTest {

    private final KandoMongoDatabase kandoDB = new KandoMongoDatabase();
    private final TaskDataAccessObject dao = new TaskDataAccessObject(kandoDB);

    // ------------------------------------------------------------
    // SUCCESS CASE
    // ------------------------------------------------------------
    @Test
    void successDeleteTask() {
        String leaderId = null;
        String teamId = null;
        String taskId = null;

        // Leader
        TeamLeader leader = new TeamLeader("Leader Name", "leader@mail.com", "Leader", "pw");
        kandoDB.add(leader);
        leaderId = leader.getIdx();

        // Team
        Team team = new Team(leaderId);
        kandoDB.add(team);
        teamId = team.getIdx();

        // Task
        Task task = new Task("Title", "Desc", 0);
        kandoDB.add(task);
        taskId = task.getIdx();

        // Attach task to team
        team.addTask(taskId);
        kandoDB.update("teams", teamId, "tasks", team.getTasks());

        // Presenter
        DeleteTaskOutputBoundary presenter = new DeleteTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(DeleteTaskOutputData data) {
                Assertions.assertTrue(data.isSuccess());
                Assertions.assertEquals("Task deleted", data.getMessage());
            }

            @Override
            public void prepareFailView(DeleteTaskOutputData data) {
                Assertions.fail("Unexpected failure: " + data.getMessage());
            }
        };

        // Run interactor
        DeleteTaskInteractor interactor = new DeleteTaskInteractor(dao, presenter);
        DeleteTaskInputData input = new DeleteTaskInputData(teamId, leaderId, taskId);
        interactor.execute(input);

        // Verify DB state
        Optional<Team> refreshedTeam = dao.getTeam(teamId);
        Assertions.assertTrue(refreshedTeam.isPresent());
        Assertions.assertFalse(refreshedTeam.get().getTasks().contains(taskId));
    }

    // ------------------------------------------------------------
    // FAIL: NOT LEADER
    // ------------------------------------------------------------
    @Test
    void failNotLeader() {
        String leaderId = null;
        String teamId = null;
        String taskId = null;
        String memberId = null;

        // Leader
        TeamLeader leader = new TeamLeader("Leader Name", "leader@mail.com", "Leader", "pw");
        kandoDB.add(leader);
        leaderId = leader.getIdx();

        // Team
        Team team = new Team(leaderId);
        kandoDB.add(team);
        teamId = team.getIdx();

        // Task
        Task task = new Task("Title", "Desc", 0);
        kandoDB.add(task);
        taskId = task.getIdx();

        team.addTask(taskId);
        kandoDB.update("teams", teamId, "tasks", team.getTasks());

        // Non-leader member
        User member = new User("Bob", "bob@mail.com", "Member", "pw");
        kandoDB.add(member);
        memberId = member.getIdx();

        DeleteTaskOutputBoundary presenter = new DeleteTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(DeleteTaskOutputData data) {
                Assertions.fail("Should NOT succeed when non-leader deletes task");
            }

            @Override
            public void prepareFailView(DeleteTaskOutputData data) {
                Assertions.assertEquals("Only team leader may delete tasks.", data.getMessage());
            }
        };

        DeleteTaskInteractor interactor = new DeleteTaskInteractor(dao, presenter);
        DeleteTaskInputData input = new DeleteTaskInputData(teamId, memberId, taskId);
        interactor.execute(input);
    }

    // ------------------------------------------------------------
    // FAIL: TASK NOT FOUND
    // ------------------------------------------------------------
    @Test
    void failTaskNotFound() {
        String leaderId = null;
        String teamId = null;

        // Leader
        TeamLeader leader = new TeamLeader("Leader", "leader@mail.com", "Leader", "pw");
        kandoDB.add(leader);
        leaderId = leader.getIdx();

        // Team
        Team team = new Team(leaderId);
        kandoDB.add(team);
        teamId = team.getIdx();

        DeleteTaskOutputBoundary presenter = new DeleteTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(DeleteTaskOutputData data) {
                Assertions.fail("Task does not exist — should NOT succeed");
            }

            @Override
            public void prepareFailView(DeleteTaskOutputData data) {
                Assertions.assertEquals("Task not found", data.getMessage());
            }
        };

        DeleteTaskInteractor interactor = new DeleteTaskInteractor(dao, presenter);

        // Nonexistent ObjectId-like string
        DeleteTaskInputData input =
                new DeleteTaskInputData(teamId, leaderId, "000000000000000000000000");

        interactor.execute(input);
    }
}
