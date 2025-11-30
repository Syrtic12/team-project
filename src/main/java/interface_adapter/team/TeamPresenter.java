
package interface_adapter.team;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_task.CreateTaskState;
import interface_adapter.create_task.CreateTaskViewModel;
import interface_adapter.edit_task.EditTaskState;
import interface_adapter.edit_task.EditTaskViewModel;
import interface_adapter.manage_team.ManageTeamState;
import interface_adapter.team.TeamState;
import interface_adapter.team.TeamViewModel;
import interface_adapter.manage_team.ManageTeamViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.create_task.CreateTaskViewModel;
import use_case.team.TeamOutputBoundary;
import use_case.team.TeamOutputData;
import use_case.create_task.CreateTaskOutputData;

import java.util.List;


public class TeamPresenter implements TeamOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;
    private final ManageTeamViewModel manageTeamViewModel;
    private final TeamViewModel teamViewModel;
    private final CreateTaskViewModel createTaskViewModel;
    private final EditTaskViewModel editTaskViewModel;

    public TeamPresenter(ViewManagerModel viewManagerModel, LoggedInViewModel loggedInViewModel,
                         ManageTeamViewModel manageTeamViewModel, TeamViewModel teamViewModel,
                         CreateTaskViewModel createTaskViewModel, EditTaskViewModel editTaskViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.manageTeamViewModel = manageTeamViewModel;
        this.teamViewModel = teamViewModel;
        this.createTaskViewModel = createTaskViewModel;
        this.editTaskViewModel = editTaskViewModel;
    }

    @Override
    public void prepareSuccessView(TeamOutputData outputData) {
        //IDK what to do here. Leave empty for now
    }

    @Override
    public void prepareFailView(String errorMessage) {
        //IDK what to do here. Leave empty for now
    }

    @Override
    public void switchToLoggedInView() {
        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToManageTeamView(List<String> teamMembers, String teamID) {
        final ManageTeamState manageTeamState = manageTeamViewModel.getState();
        manageTeamState.setMembers(teamMembers);
        manageTeamState.setTeamId(teamID);
        manageTeamState.setError(null);
        this.manageTeamViewModel.firePropertyChange();
        viewManagerModel.setState(manageTeamViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToCreateTaskView(String teamId, String invokedBy) {
        CreateTaskState state = createTaskViewModel.getState();
        state.setTeamId(teamId);
        state.setInvokedBy(invokedBy);
        createTaskViewModel.firePropertyChange();

        viewManagerModel.setState(createTaskViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToEditTaskView(String taskId, String teamId, Integer status, String title, String description) {
        EditTaskState state = editTaskViewModel.getState();
        state.setTitle(title);
        state.setDescription(description);
        state.setTaskID(taskId);
        state.setTeamID(teamId);
        state.setStatus(status);
        editTaskViewModel.firePropertyChange();

        viewManagerModel.setState(editTaskViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
