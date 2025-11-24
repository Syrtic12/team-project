package use_case.create_task;

import entity.Task;
import entity.Team;
import entity.User;

import java.util.Optional;

public class CreateTaskInteractor {

    private final CreateTaskDataAccessInterface dao;
    private final CreateTaskOutputBoundary presenter;

    public CreateTaskInteractor(CreateTaskDataAccessInterface dao, CreateTaskOutputBoundary presenter) {
        this.dao = dao;
        this.presenter = presenter;
    }

    public void execute(CreateTaskInputData inputData) {
        Optional<Team> teamOpt = dao.getTeam(inputData.getTeamId());
        if (teamOpt.isEmpty()) {
            presenter.prepareFailView(new CreateTaskOutputData(false, null, "Team not found"));
            return;
        }
        Team team = teamOpt.get();

    Optional<User> userOpt = dao.getUser(inputData.getInvokedBy());
    if (userOpt.isEmpty()) {
            presenter.prepareFailView(new CreateTaskOutputData(false, null, "User not found"));
            return;
        }
    // existence of user verified by getUser; user object not needed further here

        // ensure the creator is the team's leader by comparing ids
    if (team.getLeaderIdx() == null || !inputData.getInvokedBy().equals(team.getLeaderIdx())) {
            presenter.prepareFailView(new CreateTaskOutputData(false, null, "Only team leader can create tasks"));
            return;
        }

    // Create Task using the input's title, description, and status
    Task task = new Task(inputData.getTitle(), inputData.getDescription(), inputData.getStatus());
        Task created = dao.addTask(task);
        dao.attachTaskToTeam(created.getIdx(), team);

        // on success, call the success view with the created task index
        presenter.prepareSuccessView(new CreateTaskOutputData(true, created.getIdx(), "Task created"));
    }
}
