package interface_adapter.edit_task;

import interface_adapter.ViewModel;

public class EditTaskViewModel extends ViewModel<EditTaskState>{
    public EditTaskViewModel() {
        super("edit task");
        setState(new EditTaskState());
    }
}