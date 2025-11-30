package interface_adapter.create_task;

import interface_adapter.ViewManagerModel;
import interface_adapter.team.TeamState;
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
    public void prepareSuccessView(CreateTaskOutputData outputData) {

        // Reset state
        CreateTaskState cleared = new CreateTaskState();
        createTaskViewModel.setState(cleared);

        // Set new team variables
        final TeamState teamState = teamViewModel.getState();
        teamState.setNotStartedTasks(outputData.getNotStartedTasks());
        teamState.setInProgressTasks(outputData.getInProgressTasks());
        teamState.setCompletedTasks(outputData.getCompletedTasks());
        this.teamViewModel.firePropertyChange();
        // Switch to team view
        viewManagerModel.setState(teamViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(CreateTaskOutputData outputData) {
        CreateTaskState state = createTaskViewModel.getState();
        state.setError(outputData.getMessage());
        createTaskViewModel.setState(state);
        createTaskViewModel.firePropertyChange();
    }

    @Override
    public void switchToTeamView() {
        viewManagerModel.setState(teamViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
