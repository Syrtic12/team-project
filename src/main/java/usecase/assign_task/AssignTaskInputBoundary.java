package usecase.assign_task;

/**
 * Interface representing the input boundary for the "Assign Task" use case.
 * This defines the contract for handling the assignment of a user to a task.
 * Implementations of this interface contain the application's business logic
 * for task assignment.
 */
public interface AssignTaskInputBoundary {

    /**
     * Executes the use case for assigning a user to a task.
     *
     * @param assignTaskInputData an {@link AssignTaskInputData} object containing
     *                            the necessary information to assign the user,
     *                            such as task ID and user ID.
     */
    void execute(AssignTaskInputData assignTaskInputData);
}
