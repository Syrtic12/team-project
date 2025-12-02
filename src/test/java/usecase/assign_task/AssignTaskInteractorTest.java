package usecase.assign_task;

import dataaccess.AssignTaskDataAccessObject;
import dataaccess.KandoMongoDatabase;
import entity.Task;
import entity.Team;
import entity.TeamLeader;
import entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssignTaskInteractorTest {

    private final KandoMongoDatabase kandoDB = new KandoMongoDatabase();
    private final AssignTaskDataAccessObject dao = new AssignTaskDataAccessObject(kandoDB);

    @Test
    void successAssignTask() {
        TeamLeader leader = new TeamLeader("Leader", "leader@mail.com", "Leader", "pw");
        kandoDB.add(leader);
        String leaderId = leader.getIdx();

        Team team = new Team(leaderId);
        kandoDB.add(team);

        User member = new User("Member", "member@mail.com", "Member", "pw");
        kandoDB.add(member);
        String memberId = member.getIdx();

        Task task = new Task("Task Title", "Task Description", 0);
        kandoDB.add(task);
        String taskId = task.getIdx();

        AssignTaskOutputBoundary presenter = new AssignTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(AssignTaskOutputData data) {
                Assertions.assertEquals(taskId, data.getTaskId());
                Assertions.assertEquals(memberId, data.getTeamMemberId());
            }

            @Override
            public void prepareFailView(String message) {
                Assertions.fail("Unexpected failure: " + message);
            }
        };

        AssignTaskInteractor interactor = new AssignTaskInteractor(dao, presenter);
        AssignTaskInputData input = new AssignTaskInputData(taskId, memberId, leaderId);
        interactor.execute(input);

        Task refreshedTask = dao.getTask(taskId);
        Assertions.assertNotNull(refreshedTask);
        Assertions.assertTrue(refreshedTask.isUserAssigned(memberId));
    }

    @Test
    void failTaskNotFound() {
        TeamLeader leader = new TeamLeader("Leader", "leader@mail.com", "Leader", "pw");
        kandoDB.add(leader);
        String leaderId = leader.getIdx();

        User member = new User("Member", "member@mail.com", "Member", "pw");
        kandoDB.add(member);
        String memberId = member.getIdx();

        AssignTaskOutputBoundary presenter = new AssignTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(AssignTaskOutputData data) {
                Assertions.fail("Should NOT succeed");
            }

            @Override
            public void prepareFailView(String message) {
                Assertions.assertEquals("Task not found", message);
            }
        };

        AssignTaskInteractor interactor = new AssignTaskInteractor(dao, presenter);
        AssignTaskInputData input = new AssignTaskInputData("000000000000000000000000", memberId, leaderId);
        interactor.execute(input);
    }

    @Test
    void failTeamMemberNotFound() {
        TeamLeader leader = new TeamLeader("Leader", "leader@mail.com", "Leader", "pw");
        kandoDB.add(leader);
        String leaderId = leader.getIdx();

        Task task = new Task("Task Title", "Task Description", 0);
        kandoDB.add(task);
        String taskId = task.getIdx();

        AssignTaskOutputBoundary presenter = new AssignTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(AssignTaskOutputData data) {
                Assertions.fail("Should NOT succeed");
            }

            @Override
            public void prepareFailView(String message) {
                Assertions.assertEquals("Team member not found", message);
            }
        };

        AssignTaskInteractor interactor = new AssignTaskInteractor(dao, presenter);
        AssignTaskInputData input = new AssignTaskInputData(taskId, "000000000000000000000000", leaderId);
        interactor.execute(input);
    }

    @Test
    void failTeamLeaderNotFound() {
        User member = new User("Member", "member@mail.com", "Member", "pw");
        kandoDB.add(member);
        String memberId = member.getIdx();

        Task task = new Task("Task Title", "Task Description", 0);
        kandoDB.add(task);
        String taskId = task.getIdx();

        AssignTaskOutputBoundary presenter = new AssignTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(AssignTaskOutputData data) {
                Assertions.fail("Should NOT succeed");
            }

            @Override
            public void prepareFailView(String message) {
                Assertions.assertEquals("Team Leader not found", message);
            }
        };

        AssignTaskInteractor interactor = new AssignTaskInteractor(dao, presenter);
        AssignTaskInputData input = new AssignTaskInputData(taskId, memberId, "000000000000000000000000");
        interactor.execute(input);
    }

    @Test
    void failTaskNotBelongToTeam() {
        TeamLeader leader = new TeamLeader("Leader", "leader@mail.com", "Leader", "pw");
        kandoDB.add(leader);
        String leaderId = leader.getIdx();

        User member = new User("Member", "member@mail.com", "Member", "pw");
        kandoDB.add(member);
        String memberId = member.getIdx();

        Task task = new Task("Task Title", "Task Description", 0);
        kandoDB.add(task);
        String taskId = task.getIdx();

        AssignTaskOutputBoundary presenter = new AssignTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(AssignTaskOutputData data) {
                Assertions.fail("Should NOT succeed");
            }

            @Override
            public void prepareFailView(String message) {
                Assertions.assertEquals("This task does not belong to any team", message);
            }
        };

        AssignTaskInteractor interactor = new AssignTaskInteractor(dao, presenter);
        AssignTaskInputData input = new AssignTaskInputData(taskId, memberId, leaderId);
        interactor.execute(input);
    }

    @Test
    void failNotTeamLeader() {
        TeamLeader leader = new TeamLeader("Leader", "leader@mail.com", "Leader", "pw");
        kandoDB.add(leader);
        String leaderId = leader.getIdx();

        User member = new User("Member", "member@mail.com", "Member", "pw");
        kandoDB.add(member);
        String memberId = member.getIdx();

        User nonLeader = new User("NonLeader", "nonleader@mail.com", "NonLeader", "pw");
        kandoDB.add(nonLeader);
        String nonLeaderId = nonLeader.getIdx();

        Task task = new Task("Task Title", "Task Description", 0);
        kandoDB.add(task);
        String taskId = task.getIdx();

        AssignTaskOutputBoundary presenter = new AssignTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(AssignTaskOutputData data) {
                Assertions.fail("Should NOT succeed");
            }

            @Override
            public void prepareFailView(String message) {
                Assertions.assertEquals("Only team leader can assign task members", message);
            }
        };

        AssignTaskInteractor interactor = new AssignTaskInteractor(dao, presenter);
        AssignTaskInputData input = new AssignTaskInputData(taskId, memberId, nonLeaderId);
        interactor.execute(input);
    }

    @Test
    void failUserAlreadyAssigned() {
        TeamLeader leader = new TeamLeader("Leader", "leader@mail.com", "Leader", "pw");
        kandoDB.add(leader);
        String leaderId = leader.getIdx();

        User member = new User("Member", "member@mail.com", "Member", "pw");
        kandoDB.add(member);
        String memberId = member.getIdx();

        Task task = new Task("Task Title", "Task Description", 0);
        kandoDB.add(task);
        String taskId = task.getIdx();
        task.assignUser(memberId);

        AssignTaskOutputBoundary presenter = new AssignTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(AssignTaskOutputData data) {
                Assertions.fail("Should NOT succeed");
            }

            @Override
            public void prepareFailView(String message) {
                Assertions.assertEquals("Team member already assigned to this task", message);
            }
        };

        AssignTaskInteractor interactor = new AssignTaskInteractor(dao, presenter);
        AssignTaskInputData input = new AssignTaskInputData(taskId, memberId, leaderId);
        interactor.execute(input);
    }
}

