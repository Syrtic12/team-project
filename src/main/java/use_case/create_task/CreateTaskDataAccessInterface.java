package use_case.create_task;

import entity.Task;
import entity.Team;
import entity.User;
import java.util.Optional;

public interface CreateTaskDataAccessInterface {
    Optional<Team> getTeam(String teamId);
    Optional<User> getUser(String userId);
    Task addTask(Task task);           // returns task with idx set
    void attachTaskToTeam(String taskIdx, Team team);
    Optional<Task> getTask(String taskIdx);
}
