package adapters.create_task;

import usecase.create_task.CreateTaskInputBoundary;
import usecase.create_task.CreateTaskInputData;

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
