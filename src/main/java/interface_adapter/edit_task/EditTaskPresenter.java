
package interface_adapter.edit_task;

import interface_adapter.ViewManagerModel;
import interface_adapter.team.TeamState;
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


    /**
     * @param outputData the output data from the edit task use case
     */
    @Override
    public void prepareSuccessView(EditTaskOutputData outputData) {
        EditTaskState state = editTaskViewModel.getState();
        TeamState teamState = teamViewModel.getState();


        state.setError(""); // Clear any previous error
        editTaskViewModel.setState(state);

        // need to update the team view model to reflect the edited task
        // remove the old task and add the edited task from the appropriate hashmap
        replaceTask(outputData, teamState);
        teamViewModel.firePropertyChange();

        this.switchToTeamView();
    }


    /**
     * @param outputData the output data from the edit task use case
     * @param teamState the current state of the team view model
     */
    private static void replaceTask(EditTaskOutputData outputData, TeamState teamState) {
        if (outputData.getOldStatus() == 0) {
            teamState.getNotStartedTasks().remove(outputData.getEditedTask().getIdx());
        } else if (outputData.getOldStatus() == 1) {
            teamState.getInProgressTasks().remove(outputData.getEditedTask().getIdx());
        } else if (outputData.getOldStatus() == 2) {
            teamState.getCompletedTasks().remove(outputData.getEditedTask().getIdx());
        }

        if (outputData.getEditedTask().getStatus() == 0) {
            teamState.getNotStartedTasks().put(outputData.getEditedTask().getIdx(),
                    outputData.getEditedTask().getTitle());
        } else if (outputData.getEditedTask().getStatus() == 1) {
            teamState.getInProgressTasks().put(outputData.getEditedTask().getIdx(),
                    outputData.getEditedTask().getTitle());
        } else if (outputData.getEditedTask().getStatus() == 2) {
            teamState.getCompletedTasks().put(outputData.getEditedTask().getIdx(),
                    outputData.getEditedTask().getTitle());
        }
    }

    /**
     * @param outputData the output data from the edit task use case
     */
    @Override
    public void prepareFailView(EditTaskOutputData outputData) {
        EditTaskState state = editTaskViewModel.getState();
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
