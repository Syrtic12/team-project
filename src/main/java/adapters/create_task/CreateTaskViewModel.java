package adapters.create_task;

import adapters.ViewModel;

public class    CreateTaskViewModel extends ViewModel<CreateTaskState> {

    public CreateTaskViewModel() {
        super("create task view");
        setState(new CreateTaskState());
    }

}
