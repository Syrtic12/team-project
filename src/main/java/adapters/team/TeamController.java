package adapters.team;
import usecase.team.TeamInputBoundary;

public class TeamController {
    private final TeamInputBoundary teamInteractor;
    public TeamController(TeamInputBoundary teamInteractor) {
        this.teamInteractor = teamInteractor;
    }
    public void execute() {

    }

    public void createTask() {
    }

    public void openManageTeam(String teamId) {
        teamInteractor.switchToManageTeamView(teamId);
    }

    public void openCreateTask() {
    }

    public void openLoggedInView() {teamInteractor.switchToLoggedInView();
    }

    public void openTask(String taskId) {
    }

    public void editTask(String taskId, String teamId, Integer status, String title, String description) {
        teamInteractor.switchToEditTaskView(taskId, teamId, status, title, description);
    }

    public void createTask(String teamId, String invokedBy) {
        teamInteractor.switchToCreateTaskView(teamId, invokedBy);
    }
}