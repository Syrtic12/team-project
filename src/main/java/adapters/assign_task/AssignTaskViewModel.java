package adapters.assign_task;

import adapters.ViewModel;

/**
 * View model for the "Assign Task" use case.
 * Holds the current state of the assign task operation and provides
 * mechanisms for notifying observers of state changes.
 */
public class AssignTaskViewModel extends ViewModel<AssignTaskState> {

    public AssignTaskViewModel() {
        super("assign task");
        setState(new AssignTaskState());
    }
}
