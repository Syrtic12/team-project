package usecase.delete_task;

public interface DeleteTaskOutputBoundary {
    void prepareSuccessView(DeleteTaskOutputData outputData);
    void prepareFailView(DeleteTaskOutputData outputData);
}
