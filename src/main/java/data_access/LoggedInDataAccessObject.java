package data_access;

import entity.User;
import entity.UserFactory;
import entity.Task;
import entity.Team;
import entity.TaskFactory;
import org.bson.Document;
import org.bson.types.ObjectId;
import use_case.logged_in.LoggedInDataAccessInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoggedInDataAccessObject implements LoggedInDataAccessInterface {
    private KandoMongoDatabase GeneralDataAccessObject;
    private final TaskFactory taskFactory = new TaskFactory();
    private final UserFactory userFactory = new UserFactory();

    public LoggedInDataAccessObject(KandoMongoDatabase dao) {
        GeneralDataAccessObject = dao;
    }


    @Override
    public List<Task> getTeamTasks(String id) {
        Document teamDoc = this.GeneralDataAccessObject.getOne("teams", "_id", id);
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

    @Override
    public Map<String, String> getTeamMembers(String teamId) {
        Map<String, String> members = new HashMap<>();
        Document teamDoc = GeneralDataAccessObject.getOne("teams", "_id", teamId);
        if (teamDoc == null) {
            return members;
        }

        List<String> userIds = teamDoc.getList("users", String.class);
        if (userIds != null) {
            for (String userId : userIds) {
                Document userDoc = GeneralDataAccessObject.getOne("users", "_id", userId);
                if (userDoc != null) {
                    User user = userFactory.createFromDocument(userDoc);
                    members.put(userId, user.getEmail());
                }
            }}
        return members;
    }

    @Override
    public String getTeamLeaderId(String teamId) {
        Document teamDoc = GeneralDataAccessObject.getOne("teams", "_id", teamId);
        if (teamDoc == null) {
            return null;
        }
        return teamDoc.getString("leader");
    }
}
