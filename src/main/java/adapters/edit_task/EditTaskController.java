package adapters.edit_task;

import usecase.edit_task.EditTaskInputBoundary;
import usecase.edit_task.EditTaskInputData;

/**
 * Controller for editing a task.
 */
public class EditTaskController {

    private final EditTaskInputBoundary editTaskInteractor;

    public EditTaskController(EditTaskInputBoundary editTaskInteractor) {
        this.editTaskInteractor = editTaskInteractor;
    }

    /**
     * Executes the edit task use case.
     * @param teamId id of the team
     * @param invokedBy user who invoked the edit
     * @param taskId id of the task to be edited
     * @param title new title
     * @param description new description
     * @param status new status
     */
    public void execute(String teamId, String invokedBy, String taskId, String title, String description,
                        Integer status) {
        final EditTaskInputData editTaskInputData = new EditTaskInputData(teamId, invokedBy, taskId, title,
                description, status);
        this.editTaskInteractor.execute(editTaskInputData);
    }

    /**
     * Switches to the team view.
     */
    public void switchToTeamView() {
        editTaskInteractor.switchToTeamView();
    }

    /**
     * Deletes the current task.
     */
    public void deleteCurrentTask() {

    }
}
