package interface_adapter.edit_task;

import use_case.edit_task.EditTaskInputBoundary;
import use_case.edit_task.EditTaskInputData;

public class EditTaskController {

    private final EditTaskInputBoundary interactor;

    public EditTaskController(EditTaskInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String teamId, String invokedBy, String taskId, String description, String title) {
        EditTaskInputData input = new EditTaskInputData(
                teamId,               // View does NOT choose teamId
                invokedBy,               // View does NOT track userId
                taskId,
                title,
                description,
                null                // View does not set status
        );
        interactor.execute(input);
    }

    public void switchToTaskView() {
        // handled by parent view manager
    }
}
