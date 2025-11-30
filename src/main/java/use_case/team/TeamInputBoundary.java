package use_case.team;

public interface TeamInputBoundary {
    void execute(TeamInputData teamInputData);

    void switchToLoggedInView();

    void switchToManageTeamView();

    void switchToCreateTaskView();

    void switchToEditTaskView(String taskId, String teamId, Integer status, String title, String description);
}
