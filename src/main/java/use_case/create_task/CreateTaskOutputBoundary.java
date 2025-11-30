package use_case.create_task;

public interface CreateTaskOutputBoundary {
    void prepareSuccessView(CreateTaskOutputData outputData);
    void prepareFailView(CreateTaskOutputData outputData);
    void switchToTeamView();
}
