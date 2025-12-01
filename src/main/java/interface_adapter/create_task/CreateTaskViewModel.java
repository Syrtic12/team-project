package interface_adapter.create_task;

import interface_adapter.ViewModel;

public class CreateTaskViewModel extends ViewModel<CreateTaskState> {

    public CreateTaskViewModel() {
        super("create task view");
        setState(new CreateTaskState());
    }
}
