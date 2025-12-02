package usecase.assign_task;

import entity.Task;
import entity.Team;
import entity.User;

/**
 * Interface defining the data access operations required for the Assign Task use case.
 * Implementations provide persistence-layer interactions for retrieving tasks, users,
 * teams, and assigning users to tasks.
 *
 * @null This interface does not accept null values for method parameters.
 */
public interface AssignTaskDataAccessInterface {

    /**
     * Retrieves a Task entity by its unique ID.
     *
     * @param taskIdx the unique identifier of the task
     * @return the Task corresponding to the given ID
     */
    Task getTask(String taskIdx);

    /**
     * Retrieves a User entity by its unique ID.
     *
     * @param userIdx the unique identifier of the user
     * @return the User corresponding to the given ID
     */
    User getUser(String userIdx);

    /**
     * Retrieves the Team associated with a given Task.
     *
     * @param taskIdx the unique identifier of the task
     * @return the Team associated with the task
     */
    Team getTeamByTask(String taskIdx);

    /**
     * Checks whether a user is already assigned to a specific task.
     *
     * @param taskIdx the unique identifier of the task
     * @param userIdx the unique identifier of the user
     * @return true if the user is assigned to the task, false otherwise
     */
    boolean isUserAssignedToTask(String taskIdx, String userIdx);

    /**
     * Assigns a user to a specific task.
     *
     * @param taskIdx the unique identifier of the task
     * @param userIdx the unique identifier of the user to assign
     */
    void assignUserToTask(String taskIdx, String userIdx);
}
