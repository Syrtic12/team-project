package usecase.create_task;

public interface CreateTaskInputBoundary {
    void execute(CreateTaskInputData inputData);
    void switchToTeamView();
}
