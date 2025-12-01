
package usecase.edit_task;

public interface EditTaskOutputBoundary {
    void prepareSuccessView(EditTaskOutputData outputData);
    void prepareFailView(EditTaskOutputData outputData);
    void switchToTeamView();
}
