package use_case.create_task;

import entity.Task;
import entity.Team;
import entity.User;
import java.util.Optional;

public class CreateTaskInteractor implements CreateTaskInputBoundary {

    private final CreateTaskDataAccessInterface dataAccess;
    private final CreateTaskOutputBoundary presenter;

    public CreateTaskInteractor(CreateTaskDataAccessInterface dataAccess,
                                CreateTaskOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(CreateTaskInputData inputData) {
        Optional<Team> teamOpt = dataAccess.getTeam(inputData.getTeamId());
        if (!teamOpt.isPresent()) {
            presenter.prepareFailView(new CreateTaskOutputData(false, null, "Team not found"));
            return;
        }
        Team team = teamOpt.get();

        // check that invokedBy is leader
        Optional<User> invokerOpt = dataAccess.getUser(inputData.getInvokedBy());
        if (!invokerOpt.isPresent()) {
            presenter.prepareFailView(new CreateTaskOutputData(false, null, "User not found"));
            return;
        }
        User invoker = invokerOpt.get();

        // use TeamLeader identity stored in team repository (data access object will compare)
        // We'll rely on dataAccess/Team object semantics: if creator is not leader -> fail.
        boolean isLeader = false;
        // Attempt to determine leadership by comparing invoker idx and team leader via Team object fields
        try {
            // prefer Team.getLeader if exists
            Object leaderObj = team.getClass().getMethod("getLeader").invoke(team);
            if (leaderObj != null) {
                isLeader = leaderObj.toString().equals(invoker.getIdx()) || leaderObj.toString().equals(invoker.getName());
            }
        } catch (Exception ignore) { /* fallback below */ }

        // fallback: ask dataAccess (some DataAccessObject will check leader by team)
        if (!isLeader) {
            // many repos provide a getTeamLeader via other use cases - we ask dataAccess by retrieving leader user id
            // If the Team object doesn't expose leader plainly, dataAccess implementations should handle leadership checks
            // Here we conservatively require that team.getIdx() + invoker.getIdx() match leader in dataAccess (check below)
            // DataAccess will expose a helper below if needed. For now, if invoker name equals leader name in team JSON, allow
            try {
                // If Team has a method getUsers or getLeaderName, do a simple string compare:
                Object leaderName = team.getClass().getMethod("getLeaderName").invoke(team);
                if (leaderName != null) {
                    isLeader = leaderName.toString().equals(invoker.getName());
                }
            } catch (Exception ignore) {}
        }

        if (!isLeader) {
            presenter.prepareFailView(new CreateTaskOutputData(false, null, "Only the team leader may create tasks."));
            return;
        }

        Task newTask = new Task(inputData.getTitle(), inputData.getDescription(), inputData.getStatus());
        Task saved = dataAccess.addTask(newTask);
        // attach task idx to team
        dataAccess.attachTaskToTeam(saved.getIdx(), team);
        presenter.prepareSuccessView(new CreateTaskOutputData(true, saved.getIdx(), "Task created"));
    }
}
