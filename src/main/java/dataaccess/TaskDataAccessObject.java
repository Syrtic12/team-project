package dataaccess;

import entity.Task;
import entity.TaskFactory;
import entity.Team;
import entity.TeamFactory;
import entity.User;
import entity.UserFactory;

import org.bson.Document;

import org.bson.types.ObjectId;
import usecase.create_task.CreateTaskDataAccessInterface;
import usecase.delete_task.DeleteTaskDataAccessInterface;
import usecase.edit_task.EditTaskDataAccessInterface;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;


public class TaskDataAccessObject implements
        CreateTaskDataAccessInterface,
        DeleteTaskDataAccessInterface,
        EditTaskDataAccessInterface {

    private final KandoMongoDatabase db;

    private final TaskFactory taskFactory = new TaskFactory();
    private final TeamFactory teamFactory = new TeamFactory();
    private final UserFactory userFactory = new UserFactory();

    public TaskDataAccessObject(KandoMongoDatabase db) {
        this.db = db;
    }

    // ============================================================
    // TEAM
    // ============================================================
    @Override
    public Optional<Team> getTeam(String teamId) {
        Document doc = db.getOne("teams", "_id", teamId);
        if (doc == null) return Optional.empty();

        Team team = teamFactory.createFromDocument(doc);
        team.setIdx(doc.getObjectId("_id").toString());
        return Optional.of(team);
    }

    // ============================================================
    // USER
    // ============================================================
    @Override
    public Optional<User> getUser(String userId) {
        Document doc = db.getOne("users", "_id", userId);
        if (doc == null) return Optional.empty();

        User user = userFactory.createFromDocument(doc);
        user.setIdx(doc.getObjectId("_id").toString());
        return Optional.of(user);
    }

    // ============================================================
    // CREATE TASK
    // ============================================================
    @Override
    public Task addTask(Task task) {
        return db.add(task);
    }

    @Override
    public void attachTaskToTeam(String taskId, Team team) {
        team.addTask(taskId);
        db.update("teams", team.getIdx(), "tasks", team.getTasks());
        db.update("tasks", taskId, "teamId", team.getIdx());
    }

    // ============================================================
    // DELETE TASK
    // ============================================================
    @Override
    public Optional<Task> getTask(String taskId) {
        // STRICT lookup by _id ONLY (DeleteInteractor expects this)
        Document doc = db.getOne("tasks", "_id", taskId);
        if (doc == null) return Optional.empty();

        Task task = taskFactory.createFromDocument(doc);
        task.setIdx(doc.getObjectId("_id").toString());
        return Optional.of(task);
    }

    @Override
    public boolean removeTask(Task task) {
        return db.remove(task);
    }

    @Override
    public void detachTaskFromTeam(Task task, Team team) {
        team.removeTask(task);
        db.update("teams", team.getIdx(), "tasks", team.getTasks());
    }

    // ============================================================
    // EDIT TASK
    // ============================================================
    @Override
    public Optional<Task> getTaskByIdx(String taskId) {
        // Must support both _id AND idx — tests require this.

        // Try strict _id lookup first
        Document doc = db.getOne("tasks", "_id", taskId);
        if (doc != null) {
            Task t = taskFactory.createFromDocument(doc);
            t.setIdx(doc.getObjectId("_id").toString());
            return Optional.of(t);
        }

        // Fallback: search all for matching idx
        List<Document> docs = db.getAll("tasks");
        for (Document d : docs) {
            if (d.containsKey("idx") && taskId.equals(d.getString("idx"))) {
                Task t = taskFactory.createFromDocument(d);

                // idx is explicit here
                t.setIdx(d.getString("idx"));
                return Optional.of(t);
            }
        }

        return Optional.empty();
    }

    @Override
    public Task getTaskFromID(String id) {
        Document taskDoc = db.getOne("tasks", "_id", id);
        Task out = this.taskFactory.createFromDocument(taskDoc);
        ObjectId idx = taskDoc.getObjectId("_id");
        out.setIdx(idx.toString());
        return out;
    }

    @Override
    public List<Task> getTeamTasks(String id) {
        Document teamDoc = db.getOne("teams", "_id", id);
        if (teamDoc == null) {
            return List.of();
        }
        List<String> out = teamDoc.getList("tasks", String.class);
        if (out == null) {
            return List.of();
        }
        List<Task> results = new ArrayList<>();
        for (String taskId : out) {
            results.add(getTaskFromID(taskId));
        }
        return results;
    }

    @Override
    public void saveTask(Task task) {
        db.update("tasks", task.getIdx(), "title", task.getTitle());
        db.update("tasks", task.getIdx(), "description", task.getDescription());
        db.updateStatus("tasks", task.getIdx(), task.getStatus());
        db.update("tasks", task.getIdx(), "users", task.getAssignedUsers());
    }
}