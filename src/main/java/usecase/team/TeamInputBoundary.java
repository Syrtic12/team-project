package usecase.team;

public interface TeamInputBoundary {
    void execute(TeamInputData teamInputData);

    void switchToLoggedInView();

    void switchToManageTeamView(String teamId);

    void switchToCreateTaskView(String teamId, String invokedBy);

    void switchToEditTaskView(String taskId, String teamId, Integer status, String title, String description);
}
