package interface_adapter.edit_task;

import interface_adapter.ViewModel;

public class EditTaskViewModel extends ViewModel<EditTaskState> {

    public EditTaskViewModel() {
        super("edit task");
        setState(new EditTaskState());
    }

    public void setTask(String id, String title, String description) {
        EditTaskState state = getState();
        state.setTaskId(id);
        state.setTitle(title);
        state.setDescription(description);
        firePropertyChange();
    }
}
