package usecase.assign_task;

import entity.Task;
import entity.Team;
import entity.User;

public interface AssignTaskDataAccessInterface {
    Task getTask(String taskIdx);
    User getUser(String userIdx);
    Team getTeamByTask(String taskIdx);
    boolean isUserAssignedToTask(String taskIdx, String userIdx);
    void assignUserToTask(String taskIdx, String userIdx);
}
