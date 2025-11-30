package use_case.create_task;

import entity.Task;
import entity.Team;
import entity.User;
import use_case.team.TaskInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CreateTaskInteractor implements CreateTaskInputBoundary{

    private final CreateTaskDataAccessInterface dao;
    private final CreateTaskOutputBoundary presenter;

    public CreateTaskInteractor(CreateTaskDataAccessInterface dao, CreateTaskOutputBoundary presenter) {
        this.dao = dao;
        this.presenter = presenter;
    }

    public void execute(CreateTaskInputData inputData) {
        Optional<Team> teamOpt = dao.getTeam(inputData.getTeamId());
        if (teamOpt.isEmpty()) {
            presenter.prepareFailView(new CreateTaskOutputData(false, null, "Team not found"
                    ,null,null,null));
            return;
        }
        Team team = teamOpt.get();

    Optional<User> userOpt = dao.getUser(inputData.getInvokedBy());
    if (userOpt.isEmpty()) {
            presenter.prepareFailView(new CreateTaskOutputData(false, null, "User not found"
                    ,null,null,null));
            return;
        }
    // existence of user verified by getUser; user object not needed further here

        // ensure the creator is the team's leader by comparing ids
    if (team.getLeaderIdx() == null || !inputData.getInvokedBy().equals(team.getLeaderIdx())) {
            presenter.prepareFailView(new CreateTaskOutputData(false, null,
                    "Only team leader can create tasks",null,null,
                    null));
            return;
        }

        if (inputData.getTitle() == null || inputData.getTitle().isEmpty()) {
            presenter.prepareFailView(new CreateTaskOutputData(false, null,
                    "A title is required",null,null,
                    null));
            return;
        }

    // Create Task using the input's title, description, and status
    Task task = new Task(inputData.getTitle(), inputData.getDescription(), inputData.getStatus());
        Task created = dao.addTask(task);
        dao.attachTaskToTeam(created.getIdx(), team);
        List<Task> tasks = dao.getTeamTasks(inputData.getTeamId());
        Map<String, TaskInfo> notStartedTasks = new HashMap<>();
        Map<String, TaskInfo> inProgressTasks = new HashMap<>();
        Map<String, TaskInfo> CompletedTasks = new HashMap<>();

        for (Task listedtask : tasks) {
            List<String> assignedUsers = task.getAssignedUsers();
            String names = String.join(", ", assignedUsers);
            if (assignedUsers.isEmpty()) {
                names = "no users assigned";
            }
            TaskInfo info = new TaskInfo(listedtask.getIdx(), listedtask.getTitle(), listedtask.getDescription(),
                    names);

            if (task.getStatus()==0){
                notStartedTasks.put(listedtask.getIdx(),info);
            }
            else if (task.getStatus()==1){
                inProgressTasks.put(listedtask.getIdx(), info);
            }
            else if (task.getStatus()==2){
                CompletedTasks.put(listedtask.getIdx(), info);
            }
        }
        // on success, call the success view with the created task index
        presenter.prepareSuccessView(new CreateTaskOutputData(true, created.getIdx(), "Task created",
                notStartedTasks, inProgressTasks, CompletedTasks));
    }

    @Override
    public void switchToTeamView() {
        presenter.switchToTeamView();
    }
}
