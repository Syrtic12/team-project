package usecase.assign_task;

/**
 * Output boundary interface for the "Assign Task" use case.
 * Defines methods for presenting the results of a task assignment,
 * either success or failure. Implementations of this interface are
 * responsible for formatting and delivering output to the user interface
 * or other presentation layer.
 */
public interface AssignTaskOutputBoundary {

    /**
     * Prepares the success view when a task has been successfully assigned.
     *
     * @param assignTaskOutputData an {@link AssignTaskOutputData} object containing
     *                             the details of the successfully assigned task
     */
    void prepareSuccessView(AssignTaskOutputData assignTaskOutputData);

    /**
     * Prepares the failure view when assigning a task fails.
     *
     * @param message a descriptive error message explaining why the assignment failed
     */
    void prepareFailView(String message);
}
