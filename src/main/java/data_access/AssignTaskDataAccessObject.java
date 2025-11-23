package data_access;

import entity.Task;
import entity.TaskFactory;
import entity.User;
import entity.UserFactory;
import entity.Team;
import entity.TeamFactory;
import use_case.assign_task.AssignTaskDataAccessInterface;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class AssignTaskDataAccessObject implements AssignTaskDataAccessInterface {
    private final KandoMongoDatabase GeneralDataAccessObject;
    private final TaskFactory taskFactory = new TaskFactory();
    private final UserFactory userFactory = new UserFactory();
    private final TeamFactory teamFactory = new TeamFactory();

    public AssignTaskDataAccessObject(KandoMongoDatabase dao) {
        this.GeneralDataAccessObject = dao;
    }

    @Override
    public Task getTask(String taskIdx) {
        Document taskDocument = GeneralDataAccessObject.getOne("tasks", "_id",  taskIdx);
        if (taskDocument == null){
            return null;
        }
        return taskFactory.createFromDocument(taskDocument);
    }

    @Override
    public User getUser(String userIdx) {
        Document userDocument = GeneralDataAccessObject.getOne("users", "_id",  userIdx);
        if (userDocument == null){
            return null;
        }
        return userFactory.createFromDocument(userDocument);
    }

    @Override
    public Team getTeamByTask(String taskIdx) {
        List<Document> allTeams = GeneralDataAccessObject.getAll("teams");

        for (Document teamDocument : allTeams) {
            List<String> tasks = teamDocument.getList("tasks", String.class);
            if (tasks != null  && tasks.contains(taskIdx)){
                return teamFactory.createFromDocument(teamDocument);
            }
        }
        return null;
    }

    @Override
    public boolean isUserAssignedToTask(String taskIdx, String userIdx) {
        Task task = getTask(taskIdx);
        if (task == null) {
            return false;
        }
        return task.isUserAssigned(userIdx);
    }

    @Override
    public void assignUserToTask(String taskIdx, String userIdx) {
        Document taskDocument = GeneralDataAccessObject.getOne("users", "_id",  taskIdx);
        if (taskDocument == null) return;

        List<String> users = taskDocument.getList("users", String.class);
        if (users == null) {
            users = new ArrayList<>();
        }

        if (!users.contains(userIdx)) {
            users.add(userIdx);

            GeneralDataAccessObject.update("tasks", taskIdx, "users", users);
        }
    }
}

