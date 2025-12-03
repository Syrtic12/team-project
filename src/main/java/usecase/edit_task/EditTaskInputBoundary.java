package usecase.edit_task;

/**
 * Input Boundary for actions which are related to editing a task.
 */
public interface EditTaskInputBoundary {
    /**
     * Executes the edit task use case.
     * @param inputData the input data
     */
    void execute(EditTaskInputData inputData);

    /**
     * Switches to the team view.
     */
    void switchToTeamView();
}
