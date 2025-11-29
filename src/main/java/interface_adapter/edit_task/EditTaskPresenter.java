package interface_adapter.edit_task;

import interface_adapter.ViewManagerModel;
import interface_adapter.team.TeamViewModel;
import use_case.edit_task.EditTaskOutputBoundary;
import use_case.edit_task.EditTaskOutputData;

public class EditTaskPresenter implements EditTaskOutputBoundary {

    private final EditTaskViewModel editTaskViewModel;
    private final ViewManagerModel viewManagerModel;
    private final TeamViewModel teamViewModel;

    public EditTaskPresenter(EditTaskViewModel editTaskViewModel, ViewManagerModel viewManagerModel,
                             TeamViewModel teamViewModel) {
        this.editTaskViewModel = editTaskViewModel;
        this.viewManagerModel = viewManagerModel;
        this.teamViewModel = teamViewModel;
    }

    @Override
    public void prepareSuccessView(EditTaskOutputData outputData) {
        EditTaskState state = editTaskViewModel.getState();
        state.setError(""); // Clear any previous error
        editTaskViewModel.setState(state);

        // need to update the team view model to reflect the edited task
    }

    @Override
    public void prepareFailView(EditTaskOutputData outputData) {
        EditTaskState state = editTaskViewModel.getState();
        state.setError(outputData.getMessage());
        editTaskViewModel.setState(state);
    }

    @Override
    public void switchToTeamView() {
        viewManagerModel.setState(teamViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
