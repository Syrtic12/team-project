package data_access;

import entity.Task;
import entity.Team;
import entity.TaskFactory;
import org.bson.Document;
import org.bson.types.ObjectId;
import use_case.logged_in.LoggedInDataAccessInterface;

import java.util.List;

public class LoggedInDataAccessObject implements LoggedInDataAccessInterface {
    private KandoMongoDatabase GeneralDataAccessObject;
    private final TaskFactory taskFactory = new TaskFactory();

    public LoggedInDataAccessObject(KandoMongoDatabase dao) {
        GeneralDataAccessObject = dao;
    }


    @Override
    public List<Task> getTeamTasks(String id) {
        Document out = GeneralDataAccessObject.getOne("teams", "tasks", id);
        List<Task> tasks = (List<Task>) out.get("tasks");
        return tasks;
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