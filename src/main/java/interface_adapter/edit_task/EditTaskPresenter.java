package interface_adapter.edit_task;

import use_case.edit_task.EditTaskOutputBoundary;
import use_case.edit_task.EditTaskOutputData;

public class EditTaskPresenter implements EditTaskOutputBoundary {

    private final EditTaskViewModel viewModel;

    public EditTaskPresenter(EditTaskViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(EditTaskOutputData data) {
        EditTaskState state = viewModel.getState();
        state.setError(null);
        viewModel.setState(state);
        viewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(EditTaskOutputData data) {
        EditTaskState state = viewModel.getState();
        state.setError(data.getMessage());
        viewModel.setState(state);
        viewModel.firePropertyChange();
    }
}
