package use_case.assign_task;

import entity.Task;
import entity.User;

public interface AssignTaskDataAccessInterface {
    Task getTask(String taskIdx);
    User getUser(String userIdx);
    boolean isUserAssignedToTask(String taskIdx, String userIdx);
    void assignUserToTask(String taskIdx, String userIdx);
}
