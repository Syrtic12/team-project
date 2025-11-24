package use_case.edit_task;

import data_access.KandoMongoDatabase;
import data_access.TaskDataAccessObject;
import entity.Task;
import entity.Team;
import entity.TeamLeader;
import entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class EditTaskInteractorTest {

    private final KandoMongoDatabase kandoDB = new KandoMongoDatabase();
    private final TaskDataAccessObject dao = new TaskDataAccessObject(kandoDB);

    // Helper: attach task to team so edit tests don't fail
    private void attachTaskToTeam(String taskId, String teamId) {
        kandoDB.update("tasks", taskId, "teamId", teamId);
        Optional<Team> teamOpt = dao.getTeam(teamId);
        if (teamOpt.isPresent()) {
            Team t = teamOpt.get();
            t.addTask(taskId);
            kandoDB.update("teams", teamId, "tasks", t.getTasks());
        }
    }

    // ------------------------------------------------------------
    // SUCCESS: ASSIGNED USER EDITS TASK
    // ------------------------------------------------------------
    @Test
    void successEditTaskByAssignedUser() {
        // Create user
        User user = new User("Alice", "alice@mail.com", "Member", "pw");
        kandoDB.add(user);
        String userId = user.getIdx();

        // Create team with user as leader
        Team team = new Team(userId);
        kandoDB.add(team);
        String teamId = team.getIdx();

        // Create task assigned to user
        Task task = new Task("Old Title", "Old Description", 0);
        task.assignUser(userId);
        kandoDB.add(task);
        String taskId = task.getIdx();

        // Required fix: attach to team
        attachTaskToTeam(taskId, teamId);

        // Presenter
        EditTaskOutputBoundary presenter = new EditTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(EditTaskOutputData data) {
                Assertions.assertTrue(data.isSuccess());
                Assertions.assertEquals("Task updated", data.getMessage());
            }

            @Override
            public void prepareFailView(EditTaskOutputData data) {
                Assertions.fail("Unexpected failure: " + data.getMessage());
            }
        };

        EditTaskInteractor interactor = new EditTaskInteractor(dao, presenter);

        EditTaskInputData input = new EditTaskInputData(
                teamId,
                userId,
                taskId,
                "New Title",
                "New Description",
                1
        );

        interactor.execute(input);

        // Verify DB
        Optional<Task> refreshedOpt = dao.getTaskByIdx(taskId);
        Assertions.assertTrue(refreshedOpt.isPresent());
        Task refreshed = refreshedOpt.get();

        Assertions.assertEquals("New Title", refreshed.getTitle());
        Assertions.assertEquals("New Description", refreshed.getDescription());
        Assertions.assertEquals(1, refreshed.getStatus());
    }

    // ------------------------------------------------------------
    // FAIL: TASK NOT FOUND
    // ------------------------------------------------------------
    @Test
    void failTaskNotFound() {
        // Valid user
        User user = new User("Bob", "bob@mail.com", "Member", "pw");
        kandoDB.add(user);
        String userId = user.getIdx();

        // Valid team
        Team team = new Team(userId);
        kandoDB.add(team);
        String teamId = team.getIdx();

        EditTaskOutputBoundary presenter = new EditTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(EditTaskOutputData data) {
                Assertions.fail("Task does not exist — should NOT succeed");
            }

            @Override
            public void prepareFailView(EditTaskOutputData data) {
                Assertions.assertEquals("Task not found", data.getMessage());
            }
        };

        EditTaskInteractor interactor = new EditTaskInteractor(dao, presenter);

        EditTaskInputData input = new EditTaskInputData(
                teamId,
                userId,
                "000000000000000000000000",
                "New Title",
                "New Description",
                1
        );

        interactor.execute(input);
    }

    // ------------------------------------------------------------
    // FAIL: USER NOT FOUND
    // ------------------------------------------------------------
    @Test
    void failUserNotFound() {
        // Real leader
        TeamLeader leader = new TeamLeader("Leader", "leader@mail.com", "Leader", "pw");
        kandoDB.add(leader);
        String leaderId = leader.getIdx();

        // Team led by leader
        Team team = new Team(leaderId);
        kandoDB.add(team);
        String teamId = team.getIdx();

        // Task
        Task task = new Task("Title", "Desc", 0);
        kandoDB.add(task);
        String taskId = task.getIdx();

        // Required fix
        attachTaskToTeam(taskId, teamId);

        EditTaskOutputBoundary presenter = new EditTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(EditTaskOutputData data) {
                Assertions.fail("User does not exist — should NOT succeed");
            }

            @Override
            public void prepareFailView(EditTaskOutputData data) {
                Assertions.assertEquals("User not found", data.getMessage());
            }
        };

        EditTaskInteractor interactor = new EditTaskInteractor(dao, presenter);

        EditTaskInputData input = new EditTaskInputData(
                teamId,
                "000000000000000000000000", // nonexistent user
                taskId,
                "New Title",
                "New Description",
                1
        );

        interactor.execute(input);
    }

    // ------------------------------------------------------------
    // FAIL: NOT ASSIGNED AND NOT LEADER
    // ------------------------------------------------------------
    @Test
    void failNotAssignedAndNotLeader() {
        // Leader
        TeamLeader leader = new TeamLeader("Leader", "leader@mail.com", "Leader", "pw");
        kandoDB.add(leader);
        String leaderId = leader.getIdx();

        // Team
        Team team = new Team(leaderId);
        kandoDB.add(team);
        String teamId = team.getIdx();

        // Task with NO assigned users
        Task task = new Task("Title", "Desc", 0);
        kandoDB.add(task);
        String taskId = task.getIdx();

        // Required fix
        attachTaskToTeam(taskId, teamId);

        // Intruder
        User intruder = new User("Mallory", "mallory@mail.com", "Member", "pw");
        kandoDB.add(intruder);
        String intruderId = intruder.getIdx();

        EditTaskOutputBoundary presenter = new EditTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(EditTaskOutputData data) {
                Assertions.fail("Should NOT succeed when unauthorized");
            }

            @Override
            public void prepareFailView(EditTaskOutputData data) {
                Assertions.assertEquals(
                        "Only an assigned user or team leader may edit the task.",
                        data.getMessage()
                );
            }
        };

        EditTaskInteractor interactor = new EditTaskInteractor(dao, presenter);

        EditTaskInputData input = new EditTaskInputData(
                teamId,
                intruderId,
                taskId,
                "New Title",
                "New Description",
                1
        );

        interactor.execute(input);
    }
}
