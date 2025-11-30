package interface_adapter.create_task;

import use_case.create_task.CreateTaskInputBoundary;
import use_case.create_task.CreateTaskInputData;

public class CreateTaskController {

    private final CreateTaskInputBoundary createTaskInteractor;

    public CreateTaskController(CreateTaskInputBoundary createTaskInteractor) {
        this.createTaskInteractor = createTaskInteractor;
    }

    public void execute(String teamId, String invokedBy, String description, String title) {
        CreateTaskInputData createTaskInputData =
                new CreateTaskInputData(teamId, invokedBy, 0, description, title);
        createTaskInteractor.execute(createTaskInputData);
    }

    public void switchToTeamView() {
        createTaskInteractor.switchToTeamView();
    }
}
