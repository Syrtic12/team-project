package use_case.assign_task;

public interface AssignTaskOutputBoundary {

    void prepareSuccessView(AssignTaskOutputData assignTaskOutputData);

    void prepareFailView(String message);
}
