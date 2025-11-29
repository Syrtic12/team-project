package use_case.team;

import java.util.List;

public interface TeamInputBoundary {
    void execute(TeamInputData teamInputData);

    void switchToLoggedInView();

    void switchToManageTeamView(String teamId);

    void switchToCreateTaskView();

    void switchToEditTaskView(String taskId, String teamId, Integer status);
}
