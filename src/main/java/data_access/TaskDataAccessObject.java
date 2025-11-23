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
            Team t = dao.getTeam(teamId);
            return Optional.ofNullable(t);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUser(String userId) {
        try {
            User u = dao.getUser(userId);
            return Optional.ofNullable(u);
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
        // Add task's idx to team.tasks and update team in DB
        // Team has addTask(String task) method per repo
        try {
            team.getClass().getMethod("addTask", String.class).invoke(team, taskIdx);
        } catch (Exception ignore) {}
        dao.update("teams", team.getIdx(), "tasks", team.toJson().get("tasks").toString());
        dao.update("tasks", taskIdx, "teamId", team.getIdx());
    }

    // ---------- DeleteTaskDataAccessInterface ----------
    @Override
    public Optional<Task> getTask(String taskIdx) {
        // find in tasks collection; KandoMongoDatabase hasn't provided findById helper but we can scan getAll
        List<Document> docs = dao.getAll("tasks");
        for (Document d : docs) {
            Object idObj = d.get("_id");
            String idStr = idObj == null ? null : idObj.toString();
            if (idStr != null && idStr.contains(taskIdx) || (d.getString("_id") != null && d.getString("_id").equals(taskIdx))) {
                Task t = documentToTask(d);
                return Optional.of(t);
            }
            // Sometimes KandoMongo stores Id differently; also check for 'idx' field
            if (d.containsKey("idx") && taskIdx.equals(d.getString("idx"))) {
                Task t = documentToTask(d);
                return Optional.of(t);
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
        try {
            team.getClass().getMethod("removeTask", Task.class).invoke(team, task);
        } catch (Exception ignore) {}
        dao.update("teams", team.getIdx(), "tasks", team.toJson().get("tasks").toString());
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
        // update individual fields using dao.update
        // Use dao.update(collectionName, idx, key, value)
        // Update title, description, status, users
        if (task.getIdx() == null) {
            dao.add(task);
            return;
        }
        dao.update("tasks", task.getIdx(), "title", task.getTitle());
        dao.update("tasks", task.getIdx(), "description", task.getDescription());
        dao.update("tasks", task.getIdx(), "status", String.valueOf(task.getStatus()));
        // update users array: we will store as JSON string for simplicity
        dao.update("tasks", task.getIdx(), "users", task.toJson().get("users").toString());
    }

    // Helper to convert Document to Task
    private Task documentToTask(Document d) {
        String title = d.getString("title");
        String description = d.getString("description");
        Integer status = d.getInteger("status") == null ? 0 : d.getInteger("status");
        Task t = new Task(title, description, status);
        if (d.containsKey("users") && d.get("users") instanceof List) {
            List<?> list = (List<?>) d.get("users");
            for (Object o : list) {
                t.assignUser(o.toString());
            }
        } else if (d.containsKey("users")) {
            Object users = d.get("users");
            // attempt parse if JSON string
        }
        // set idx
        if (d.containsKey("_id")) {
            try {
                String s = d.get("_id").toString();
                t.setIdx(s);
            } catch (Exception ignore) {}
        } else if (d.containsKey("idx")) {
            t.setIdx(d.getString("idx"));
        }
        return t;
    }
}
