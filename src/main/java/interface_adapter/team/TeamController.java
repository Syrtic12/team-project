package interface_adapter.team;
import use_case.team.TeamInputBoundary;
import use_case.team.TeamInputData;

public class TeamController {
    private final TeamInputBoundary teamInputBoundary;
    public TeamController(TeamInputBoundary teamInputBoundary) {
        this.teamInputBoundary = teamInputBoundary;
    }

    public void createTask() {
    }

    public void openManageTeam() {
    }

    public void openCreateTask() {
    }

    public void openLoggedInView() {
    }

    public void openTask(String taskId) {
    }

    public void editTask(String taskId) {
    }
}
