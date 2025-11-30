package interface_adapter.create_task;

import interface_adapter.ViewManagerModel;
import interface_adapter.team.TeamViewModel;
import use_case.create_task.CreateTaskOutputBoundary;
import use_case.create_task.CreateTaskOutputData;

public class CreateTaskPresenter implements CreateTaskOutputBoundary {

    private final CreateTaskViewModel createTaskViewModel;
    private final ViewManagerModel viewManagerModel;
    private final TeamViewModel teamViewModel;

    public CreateTaskPresenter(CreateTaskViewModel createTaskViewModel,
                               ViewManagerModel viewManagerModel,
                               TeamViewModel teamViewModel) {
        this.createTaskViewModel = createTaskViewModel;
        this.viewManagerModel = viewManagerModel;
        this.teamViewModel = teamViewModel;
    }

    @Override
    public void prepareSuccessView(CreateTaskOutputData response) {

        // Reset state
        CreateTaskState cleared = new CreateTaskState();
        createTaskViewModel.setState(cleared);

        // Switch to team view
        viewManagerModel.setState(teamViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(CreateTaskOutputData response) {
        CreateTaskState state = createTaskViewModel.getState();
        state.setError(response.getMessage());
        createTaskViewModel.setState(state);
        createTaskViewModel.firePropertyChange();
    }

    public void switchToTeamView() {
        viewManagerModel.setState(teamViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
