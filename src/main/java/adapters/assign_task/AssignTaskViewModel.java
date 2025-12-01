package adapters.assign_task;

import adapters.ViewModel;

public class AssignTaskViewModel extends ViewModel<AssignTaskState> {

    public AssignTaskViewModel() {
        super ("assign task");
        setState(new AssignTaskState());
    }
}
