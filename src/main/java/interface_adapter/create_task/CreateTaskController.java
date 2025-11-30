package interface_adapter.create_task;

import use_case.create_task.CreateTaskInputBoundary;
import use_case.create_task.CreateTaskInputData;

public class CreateTaskController {

    private final CreateTaskInputBoundary interactor;

    public CreateTaskController(CreateTaskInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String teamId, String invokedBy, String description, String title) {
        CreateTaskInputData createTaskInputData =
                new CreateTaskInputData(teamId, invokedBy, 0, description, title);
        interactor.execute(createTaskInputData);
    }

    public void switchToTeamView() {
        interactor.switchToTeamView();
    }
}
