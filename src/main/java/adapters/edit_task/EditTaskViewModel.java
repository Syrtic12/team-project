package adapters.edit_task;

import adapters.ViewModel;

/**
 * The view model for editing a task.
 */
public class EditTaskViewModel extends ViewModel<EditTaskState> {
    public EditTaskViewModel() {
        super("edit task");
        setState(new EditTaskState());
    }
}
