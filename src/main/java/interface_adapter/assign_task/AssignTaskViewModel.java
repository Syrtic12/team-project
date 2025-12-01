package interface_adapter.assign_task;

import interface_adapter.ViewModel;

public class AssignTaskViewModel extends ViewModel<AssignTaskState> {

    public AssignTaskViewModel() {
        super ("assign task");
        setState(new AssignTaskState());
    }
}
