package usecase.assign_task;

import dataaccess.AssignTaskDataAccessObject;
import dataaccess.KandoMongoDatabase;
import entity.Task;
import entity.Team;
import entity.TeamLeader;
import entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AssignTaskInteractorTest {

    private final KandoMongoDatabase kandoDB = new KandoMongoDatabase();
    private final AssignTaskDataAccessObject dao = new AssignTaskDataAccessObject(kandoDB);

    @Test
    void failTaskNotFound() {
        String leaderId;
        String memberId;

        TeamLeader leader = new TeamLeader("Leader Name", "leader@mail.com", "Leader", "pw");
        kandoDB.add(leader);
        leaderId = leader.getIdx();

        User member = new User("Member Name", "member@mail.com", "Member", "pw");
        kandoDB.add(member);
        memberId = member.getIdx();

        AssignTaskOutputBoundary presenter = new AssignTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(AssignTaskOutputData data) {
                Assertions.fail("Task does not exist — should NOT succeed");
            }

            @Override
            public void prepareFailView(String message) {
                Assertions.assertEquals("Task not found", message);
            }
        };

        AssignTaskInteractor interactor = new AssignTaskInteractor(dao, presenter);

        AssignTaskInputData input = new AssignTaskInputData(
                "000000000000000000000000",
                memberId,
                leaderId
        );

        interactor.execute(input);
    }

    @Test
    void failTeamMemberNotFound() {
        String leaderId;
        String teamId;
        String taskId;

        TeamLeader leader = new TeamLeader("Leader Name", "leader@mail.com", "Leader", "pw");
        kandoDB.add(leader);
        leaderId = leader.getIdx();

        Team team = new Team(leaderId);
        kandoDB.add(team);
        teamId = team.getIdx();

        Task task = new Task("Task Title", "Task Description", 0);
        kandoDB.add(task);
        taskId = task.getIdx();

        team.addTask(taskId);
        kandoDB.update("teams", teamId, "tasks", team.getTasks());

        AssignTaskOutputBoundary presenter = new AssignTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(AssignTaskOutputData data) {
                Assertions.fail("Team member does not exist — should NOT succeed");
            }

            @Override
            public void prepareFailView(String message) {
                Assertions.assertEquals("Team member not found", message);
            }
        };

        AssignTaskInteractor interactor = new AssignTaskInteractor(dao, presenter);

        AssignTaskInputData input = new AssignTaskInputData(
                taskId,
                "000000000000000000000000",
                leaderId
        );

        interactor.execute(input);
    }

    @Test
    void failOnlyTeamLeaderCanAssignTask() {
        String leaderId;
        String memberId;
        String nonLeaderId;
        String teamId;
        String taskId;

        TeamLeader leader = new TeamLeader("Leader Name", "leader@mail.com", "Leader", "pw");
        kandoDB.add(leader);
        leaderId = leader.getIdx();

        User member = new User("Member Name", "member@mail.com", "Member", "pw");
        kandoDB.add(member);
        memberId = member.getIdx();

        User nonLeader = new User("Non Leader", "nonleader@mail.com", "Member", "pw");
        kandoDB.add(nonLeader);
        nonLeaderId = nonLeader.getIdx();

        Team team = new Team(leaderId);
        kandoDB.add(team);
        teamId = team.getIdx();

        Task task = new Task("Task Title", "Task Description", 0);
        kandoDB.add(task);
        taskId = task.getIdx();

        team.addTask(taskId);
        kandoDB.update("teams", teamId, "tasks", team.getTasks());

        AssignTaskOutputBoundary presenter = new AssignTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(AssignTaskOutputData data) {
                Assertions.fail("Non-leader tried to assign task — should NOT succeed");
            }

            @Override
            public void prepareFailView(String message) {
                Assertions.assertEquals("Only the team leader can assign task members", message);
            }
        };

        AssignTaskInteractor interactor = new AssignTaskInteractor(dao, presenter);

        AssignTaskInputData input = new AssignTaskInputData(
                taskId,
                memberId,
                nonLeaderId
        );

        interactor.execute(input);
    }

    @Test
    void failMemberAlreadyAssignedToTask() {
        String leaderId;
        String memberId;
        String teamId;
        String taskId;

        TeamLeader leader = new TeamLeader("Leader Name", "leader@mail.com", "Leader", "pw");
        kandoDB.add(leader);
        leaderId = leader.getIdx();

        User member = new User("Member Name", "member@mail.com", "Member", "pw");
        kandoDB.add(member);
        memberId = member.getIdx();

        Team team = new Team(leaderId);
        kandoDB.add(team);
        teamId = team.getIdx();

        Task task = new Task("Task Title", "Task Description", 0);
        task.assignUser(memberId);
        kandoDB.add(task);
        taskId = task.getIdx();

        team.addTask(taskId);
        kandoDB.update("teams", teamId, "tasks", team.getTasks());

        AssignTaskOutputBoundary presenter = new AssignTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(AssignTaskOutputData data) {
                Assertions.fail("Member already assigned to task — should NOT succeed");
            }

            @Override
            public void prepareFailView(String message) {
                Assertions.assertEquals("Team member already assigned to this task", message);
            }
        };

        AssignTaskInteractor interactor = new AssignTaskInteractor(dao, presenter);

        AssignTaskInputData input = new AssignTaskInputData(
                taskId,
                memberId,
                leaderId
        );

        interactor.execute(input);
    }

    @Test
    void successAssignTask() {
        String leaderId;
        String memberId;
        String teamId;
        String taskId;

        TeamLeader leader = new TeamLeader("Leader Name", "leader@mail.com", "Leader", "pw");
        kandoDB.add(leader);
        leaderId = leader.getIdx();

        User member = new User("Member Name", "member@mail.com", "Member", "pw");
        kandoDB.add(member);
        memberId = member.getIdx();

        Team team = new Team(leaderId);
        kandoDB.add(team);
        teamId = team.getIdx();

        Task task = new Task("Task Title", "Task Description", 0);
        kandoDB.add(task);
        taskId = task.getIdx();

        team.addTask(taskId);
        kandoDB.update("teams", teamId, "tasks", team.getTasks());

        final String finalMemberId = memberId;
        final String finalTaskId = taskId;

        AssignTaskOutputBoundary presenter = new AssignTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(AssignTaskOutputData data) {
                Assertions.assertEquals(finalTaskId, data.getTaskId());
                Assertions.assertEquals(finalMemberId, data.getTeamMemberId());
                Assertions.assertFalse(data.isAlreadyAssigned());
                Assertions.assertEquals("success", data.getMessage());
                Assertions.assertNotNull(data.getUpdatedTask());
                Assertions.assertTrue(data.getUpdatedTask().isUserAssigned(finalMemberId));
            }

            @Override
            public void prepareFailView(String message) {
                Assertions.fail("Unexpected failure: " + message);
            }
        };

        AssignTaskInteractor interactor = new AssignTaskInteractor(dao, presenter);

        AssignTaskInputData input = new AssignTaskInputData(
                taskId,
                memberId,
                leaderId
        );

        interactor.execute(input);

        Task updatedTask = dao.getTask(taskId);
        Assertions.assertNotNull(updatedTask);
        Assertions.assertTrue(updatedTask.isUserAssigned(memberId),
                "Member should be assigned to the task after successful assignment");
    }
}

