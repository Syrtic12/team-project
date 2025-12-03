package adapters.edit_task;

import adapters.ViewManagerModel;
import adapters.team.TeamState;
import adapters.team.TeamViewModel;
import usecase.edit_task.EditTaskOutputBoundary;
import usecase.edit_task.EditTaskOutputData;
import usecase.team.TaskInfo;

import java.util.List;

/**
 * Presenter for editing a task.
 */
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

    /**
     * Prepares the success view after editing a task.
     * @param outputData the output data from the edit task use case
     */
    @Override
    public void prepareSuccessView(EditTaskOutputData outputData) {
        final EditTaskState state = editTaskViewModel.getState();
        final TeamState teamState = teamViewModel.getState();

        state.setError("");
        editTaskViewModel.setState(state);

        // need to update the team view model to reflect the edited task
        // remove the old task and add the edited task from the appropriate hashmap
        replaceTask(outputData, teamState);
        teamViewModel.firePropertyChange();

        this.switchToTeamView();
    }

    /**
     * Replaces the old task with the edited task in the appropriate hashmap.
     * @param outputData the output data from the edit task use case
     * @param teamState the current state of the team view model
     */
    private static void replaceTask(EditTaskOutputData outputData, TeamState teamState) {
        if (outputData.getOldStatus() == 0) {
            teamState.getNotStartedTasks().remove(outputData.getEditedTask().getIdx());
        }
        else if (outputData.getOldStatus() == 1) {
            teamState.getInProgressTasks().remove(outputData.getEditedTask().getIdx());
        }
        else if (outputData.getOldStatus() == 2) {
            teamState.getCompletedTasks().remove(outputData.getEditedTask().getIdx());
        }
        final List<String> assignedUsers = outputData.getEditedTask().getAssignedUsers();
        final String names = String.join(", ", assignedUsers);

        final TaskInfo taskInfo = new TaskInfo(outputData.getEditedTask().getIdx(),
                outputData.getEditedTask().getTitle(), outputData.getEditedTask().getDescription(), names);
        if (outputData.getEditedTask().getStatus() == 0) {
            teamState.getNotStartedTasks().put(outputData.getEditedTask().getIdx(), taskInfo);
        }
        else if (outputData.getEditedTask().getStatus() == 1) {
            teamState.getInProgressTasks().put(outputData.getEditedTask().getIdx(), taskInfo);
        }
        else if (outputData.getEditedTask().getStatus() == 2) {
            teamState.getCompletedTasks().put(outputData.getEditedTask().getIdx(), taskInfo);
        }
    }

    /**
     * Prepares the failure view after editing a task.
     * @param outputData the output data from the edit task use case
     */
    @Override
    public void prepareFailView(EditTaskOutputData outputData) {
        final EditTaskState state = editTaskViewModel.getState();
        state.setError(outputData.getMessage());
        editTaskViewModel.setState(state);
    }

    /**
     * Switches the view to the team view.
     */
    @Override
    public void switchToTeamView() {
        viewManagerModel.setState(teamViewModel.getViewName());
        viewManagerModel.firePropertyChange();
        teamViewModel.firePropertyChange();
    }
}
