package data_access;

import entity.Task;
import org.bson.Document;
import use_case.logged_in.LoggedInDataAccessInterface;

import java.util.List;

public class LoggedInDataAccessObject implements LoggedInDataAccessInterface {
    private KandoMongoDatabase GeneralDataAccessObject;

    public LoggedInDataAccessObject(KandoMongoDatabase dao) {
        GeneralDataAccessObject = dao;
    }


    @Override
    public List<Task> getTasks(String id) {
        Document out = GeneralDataAccessObject.getOne("teams", "tasks", id);
        List<Task> tasks = (List<Task>) out.get("tasks");
        return tasks;
    }
}
