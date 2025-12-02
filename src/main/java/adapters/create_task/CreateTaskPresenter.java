package adapters.create_task;

import adapters.ViewManagerModel;
import adapters.team.TeamState;
import adapters.team.TeamViewModel;
import usecase.create_task.CreateTaskOutputBoundary;
import usecase.create_task.CreateTaskOutputData;
import usecase.logged_in.LoggedInInputBoundary;

public class CreateTaskPresenter implements CreateTaskOutputBoundary {

    private final CreateTaskViewModel createTaskViewModel;
    private final ViewManagerModel viewManagerModel;
    private final TeamViewModel teamViewModel;
    private final LoggedInInputBoundary loggedInInputBoundary;

    public CreateTaskPresenter(CreateTaskViewModel createTaskViewModel,
                               ViewManagerModel viewManagerModel,
                               TeamViewModel teamViewModel,
                               LoggedInInputBoundary loggedInInputBoundary) {
        this.createTaskViewModel = createTaskViewModel;
        this.viewManagerModel = viewManagerModel;
        this.teamViewModel = teamViewModel;
        this.loggedInInputBoundary = loggedInInputBoundary;
    }

    @Override
    public void prepareSuccessView(CreateTaskOutputData outputData) {

        // Reset state
        CreateTaskState cleared = new CreateTaskState();
        createTaskViewModel.setState(cleared);

        String teamId = teamViewModel.getState().getTeamName();
        loggedInInputBoundary.switchToTeamView(teamId);
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
