package adapters.assign_task;

import adapters.team.TeamViewModel;
import usecase.assign_task.AssignTaskOutputBoundary;
import usecase.assign_task.AssignTaskOutputData;

public class AssignTaskPresenter implements AssignTaskOutputBoundary{
    private final AssignTaskViewModel assignTaskViewModel;
    private final TeamViewModel teamViewModel;

    public AssignTaskPresenter(AssignTaskViewModel assignTaskViewModel, TeamViewModel teamViewModel) {
        this.assignTaskViewModel = assignTaskViewModel;
        this.teamViewModel = teamViewModel;
    }

    @Override
    public void prepareSuccessView(AssignTaskOutputData outputData) {
        AssignTaskState state = new AssignTaskState();
        state.setError(null);
        state.setSuccess("Task assigned successfully");
        assignTaskViewModel.setState(state);
        assignTaskViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String message) {
        AssignTaskState state = new AssignTaskState();
        state.setError(message);
        state.setSuccess(null);
        assignTaskViewModel.setState(state);
        assignTaskViewModel.firePropertyChange();
    }
}
