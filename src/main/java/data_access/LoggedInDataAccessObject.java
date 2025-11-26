package data_access;

import entity.Task;
import entity.Team;
import entity.TaskFactory;
import org.bson.Document;
import org.bson.types.ObjectId;
import use_case.logged_in.LoggedInDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class LoggedInDataAccessObject implements LoggedInDataAccessInterface {
    private KandoMongoDatabase GeneralDataAccessObject;
    private final TaskFactory taskFactory = new TaskFactory();

    public LoggedInDataAccessObject(KandoMongoDatabase dao) {
        GeneralDataAccessObject = dao;
    }


    @Override
    public List<Task> getTeamTasks(String id) {
        Document teamDoc = this.GeneralDataAccessObject.getOne("teams", "tasks", id);
        if (teamDoc == null) {
            return List.of();
        }
        List<String> out = teamDoc.getList("tasks", String.class);
        if (out == null) {
            return List.of();
        }
        List<Task> results = new ArrayList<>();
        for (String taskId : out) {
            results.add(getTask(taskId));
        }
        return results;
    }

    @Override
    public Task getTask(String id) {
        Document taskDoc = GeneralDataAccessObject.getOne("tasks", "_id", id);
        Task out = this.taskFactory.createFromDocument(taskDoc);
        ObjectId idx = taskDoc.getObjectId("_id");
        out.setIdx(idx.toString());
        return out;
    }
}
