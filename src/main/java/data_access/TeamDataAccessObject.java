package data_access;

import entity.TaskFactory;
import entity.Team;
import entity.User;
import entity.Task;
import org.bson.Document;
import org.bson.types.ObjectId;
import use_case.team.TeamDataAccessInterface;

public class TeamDataAccessObject implements TeamDataAccessInterface {
    private KandoMongoDatabase GeneralDataAccessObject;
    private final TaskFactory taskFactory = new TaskFactory();

    public TeamDataAccessObject(KandoMongoDatabase dao) {
        GeneralDataAccessObject = dao;
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
