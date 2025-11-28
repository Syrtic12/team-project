package interface_adapter.team;
import view.TeamView;

public class TeamController {
    private final TeamInputBoundary teamInteractor;
    public TeamController(TeamInputBoundary teamInteractor) {
        this.teamInteractor = teamInteractor;
    }
    public void execute() {

    }

    public TeamController() {

    }


    public void openManageTeam() {

    }

    public void openCreateTask() {

    }

    public void openLoggedInView() {

    }

    public void openTask(String taskId) {

    }

    public void editTask(String taskId, String teamId, Integer status) {
        teamInteractor.switchToEditTaskView(taskId, teamId, status);
    }
}
