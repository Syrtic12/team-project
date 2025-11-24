package data_access;

import entity.Task;
import entity.Team;
import entity.User;
import use_case.create_task.CreateTaskDataAccessInterface;
import use_case.delete_task.DeleteTaskDataAccessInterface;
import use_case.edit_task.EditTaskDataAccessInterface;

import org.bson.Document;
import java.util.*;

public class TaskDataAccessObject implements CreateTaskDataAccessInterface,
                                              DeleteTaskDataAccessInterface,
                                              EditTaskDataAccessInterface {

    private final KandoMongoDatabase dao;

    public TaskDataAccessObject(KandoMongoDatabase dao) {
        this.dao = dao;
    }

    // ---------- CreateTaskDataAccessInterface ----------
    @Override
    public Optional<Team> getTeam(String teamId) {
        try {
            return Optional.ofNullable(dao.getTeam(teamId));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUser(String userId) {
        try {
            return Optional.ofNullable(dao.getUser(userId));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Task addTask(Task task) {
        return dao.add(task);
    }

    @Override
    public void attachTaskToTeam(String taskIdx, Team team) {
        // Properly update team
        team.addTask(taskIdx);

        // Store tasks as array, NOT string
        dao.update("teams", team.getIdx(), "tasks", team.getTasks());

        // Also assign teamId to the task
        dao.update("tasks", taskIdx, "teamId", team.getIdx());
    }

    // ---------- DeleteTaskDataAccessInterface ----------
    @Override
    public Optional<Task> getTask(String taskIdx) {

        List<Document> docs = dao.getAll("tasks");

        for (Document d : docs) {

            // Check idx first (your entities use idx reliably)
            if (d.containsKey("idx") && taskIdx.equals(d.getString("idx"))) {
                return Optional.of(documentToTask(d));
            }

            // Check string match on ObjectId if needed
            if (d.containsKey("_id") && d.get("_id").toString().equals(taskIdx)) {
                return Optional.of(documentToTask(d));
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean removeTask(Task task) {
        return dao.remove(task);
    }

    @Override
    public void detachTaskFromTeam(Task task, Team team) {
        team.removeTask(task);
        dao.update("teams", team.getIdx(), "tasks", team.getTasks());
    }

    // ---------- EditTaskDataAccessInterface ----------
    @Override
    public Optional<Task> getTaskByIdx(String taskIdx) {
        return getTask(taskIdx);
    }

    public Optional<Team> getTeamForEdit(String teamId) {
        return getTeam(teamId);
    }

    @Override
    public void saveTask(Task task) {
        dao.update("tasks", task.getIdx(), "title", task.getTitle());
        dao.update("tasks", task.getIdx(), "description", task.getDescription());
        dao.updateStatus("tasks", task.getIdx(), task.getStatus());

        dao.update("tasks", task.getIdx(), "users", task.getAssignedUsers());
    }

    // Helper to convert Document to Task
    private Task documentToTask(Document d) {
        String title = d.getString("title");
        String description = d.getString("description");
        int status = d.getInteger("status", 0);

        Task t = new Task(title, description, status);

        // users array
        if (d.containsKey("users")) {
            Object usersObj = d.get("users");
            if (usersObj instanceof List<?>) {
                @SuppressWarnings("unchecked")
                List<Object> users = (List<Object>) usersObj;
                for (Object u : users) t.assignUser(u.toString());
            }
        }

        // task idx
        if (d.containsKey("idx")) t.setIdx(d.getString("idx"));
        else if (d.containsKey("_id")) t.setIdx(d.get("_id").toString());

        return t;
    }
}
