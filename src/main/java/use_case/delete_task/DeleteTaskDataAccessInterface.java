package use_case.delete_task;

import entity.Task;
import entity.Team;
import entity.User;
import java.util.Optional;

public interface DeleteTaskDataAccessInterface {
    Optional<Team> getTeam(String teamId);
    Optional<User> getUser(String userId);
    Optional<Task> getTaskByIdx(String taskIdx);
    boolean removeTask(Task task);
    void detachTaskFromTeam(Task task, Team team);
    Optional<Task> getTask(String taskIdx);
}
