package interface_adapter.edit_task;

import use_case.edit_task.EditTaskInputBoundary;
import use_case.edit_task.EditTaskInputData;

public class EditTaskController {

    private final EditTaskInputBoundary editTaskInteractor;

    public EditTaskController(EditTaskInputBoundary editTaskInteractor) {
        this.editTaskInteractor = editTaskInteractor;
    }

    public void execute(String teamId, String invokedBy, String taskId, String title, String description, Integer status) {
        final EditTaskInputData editTaskInputData = new EditTaskInputData(teamId, invokedBy, taskId, title, description, status);
        this.editTaskInteractor.execute(editTaskInputData);
    }

    public void switchToTeamView() {editTaskInteractor.switchToTeamView();}

    public void deleteCurrentTask() {

    }
}