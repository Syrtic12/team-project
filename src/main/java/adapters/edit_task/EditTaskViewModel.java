package adapters.edit_task;

import adapters.ViewModel;

public class EditTaskViewModel extends ViewModel<EditTaskState>{
    public EditTaskViewModel() {
        super("edit task");
        setState(new EditTaskState());
    }
}