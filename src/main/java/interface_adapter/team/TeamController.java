package interface_adapter.team;
import use_case.team.TeamInputBoundary;
import use_case.team.TeamInputData;

public class TeamController {
    private final TeamInputBoundary teamInteractor;
    public TeamController(TeamInputBoundary teamInteractor) {
        this.teamInteractor = teamInteractor;
    }


    public void createTask() {
    }

    public void openManageTeam() {
    }

    public void openCreateTask() {
    }

    public void openLoggedInView() {teamInteractor.switchToLoggedInView();
    }

    public void openTask(String taskId) {
    }

    public void editTask(String taskId) {
    }
}
