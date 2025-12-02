package adapters.assign_task;

import adapters.team.TeamState;
import adapters.team.TeamViewModel;
import usecase.assign_task.AssignTaskOutputBoundary;
import usecase.assign_task.AssignTaskOutputData;
import usecase.team.TaskInfo;

import java.util.List;

public class AssignTaskPresenter implements AssignTaskOutputBoundary{
    private final AssignTaskViewModel assignTaskViewModel;
    private final TeamViewModel teamViewModel;

    public AssignTaskPresenter(AssignTaskViewModel assignTaskViewModel, TeamViewModel teamViewModel) {
        this.assignTaskViewModel = assignTaskViewModel;
        this.teamViewModel = teamViewModel;
    }

    @Override
    public void prepareSuccessView(AssignTaskOutputData outputData) {
        AssignTaskState state = assignTaskViewModel.getState();
        state.setError(null);
        state.setSuccess("Task assigned successfully");
        assignTaskViewModel.setState(state);
        assignTaskViewModel.firePropertyChange();

        updateTaskInTeamView(outputData);
        teamViewModel.firePropertyChange();
    }

    public void updateTaskInTeamView(AssignTaskOutputData outputData) {
        TeamState teamState = teamViewModel.getState();

        if (outputData.getUpdatedTask() != null) {
            List<String> assignedUsers = outputData.getUpdatedTask().getAssignedUsers();
            String names = String.join(", ", assignedUsers);

            TaskInfo taskInfo = new TaskInfo(
                    outputData.getUpdatedTask().getIdx(),
                    outputData.getUpdatedTask().getTitle(),
                    outputData.getUpdatedTask().getDescription(),
                    names
            );

            int status = outputData.getUpdatedTask().getStatus();
            if (status == 0) {
                teamState.getNotStartedTasks().put(outputData.getUpdatedTask().getIdx(), taskInfo);
            } else if (status == 1) {
                teamState.getInProgressTasks().put(outputData.getUpdatedTask().getIdx(), taskInfo);
            } else if (status == 2) {
                teamState.getCompletedTasks().put(outputData.getUpdatedTask().getIdx(), taskInfo);
            }
        }
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
