package usecase.edit_task;

public interface EditTaskInputBoundary {
    void execute(EditTaskInputData inputData);
    void switchToTeamView();
}
