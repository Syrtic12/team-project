package usecase.edit_task;

import entity.Task;
import entity.Team;
import entity.User;
import java.util.Optional;

public interface EditTaskDataAccessInterface {
    Optional<Task> getTaskByIdx(String taskIdx);
    Optional<Team> getTeam(String teamId);
    Optional<User> getUser(String userId);
    void saveTask(Task task); // upsert
    Optional<Task> getTask(String taskIdx);
}
