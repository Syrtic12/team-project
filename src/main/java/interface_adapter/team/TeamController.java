package interface_adapter.team;
import use_case.team.TeamInputBoundary;

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
}