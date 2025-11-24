package interface_adapter.edit_task;

import interface_adapter.ViewModel;
import interface_adapter.create_task.CreateTaskState;
import view.EditTaskView;

public class EditTaskViewModel extends ViewModel<EditTaskState>{
    public EditTaskViewModel(EditTaskView editTaskView) {
        super("edit_task");
        setState(new EditTaskState());
    }
}
