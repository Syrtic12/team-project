package adapters.assign_task;

import adapters.team.TeamState;
import adapters.team.TeamViewModel;
import usecase.assign_task.AssignTaskOutputBoundary;
import usecase.assign_task.AssignTaskOutputData;
import usecase.logged_in.LoggedInInputBoundary;

import java.util.List;

public class AssignTaskPresenter implements AssignTaskOutputBoundary{
    private final AssignTaskViewModel assignTaskViewModel;
    private final TeamViewModel teamViewModel;
    private final LoggedInInputBoundary loggedInInputBoundary;

    public AssignTaskPresenter(AssignTaskViewModel assignTaskViewModel, TeamViewModel teamViewModel, LoggedInInputBoundary loggedInInputBoundary) {
        this.assignTaskViewModel = assignTaskViewModel;
        this.teamViewModel = teamViewModel;
        this.loggedInInputBoundary = loggedInInputBoundary;
    }

    @Override
    public void prepareSuccessView(AssignTaskOutputData outputData) {
        AssignTaskState state = assignTaskViewModel.getState();
        state.setError(null);
        state.setSuccess("Task assigned successfully");
        assignTaskViewModel.setState(state);
        assignTaskViewModel.firePropertyChange();

        String teamId = teamViewModel.getState().getTeamName();
        loggedInInputBoundary.switchToTeamView(teamId);
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
