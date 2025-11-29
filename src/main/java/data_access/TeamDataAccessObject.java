package data_access;

import entity.TaskFactory;
import entity.TeamFactory;
import entity.UserFactory;
import entity.Team;
import entity.User;
import entity.Task;
import org.bson.Document;
import org.bson.types.ObjectId;
import use_case.team.TeamDataAccessInterface;

import java.util.List;

public class TeamDataAccessObject implements TeamDataAccessInterface {
    private KandoMongoDatabase GeneralDataAccessObject;
    private final TaskFactory taskFactory = new TaskFactory();
    private final TeamFactory teamFactory = new TeamFactory();
    private final UserFactory userFactory = new UserFactory();

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

    @Override
    public List<String> getTeamMembers(Team team) {
        Document teamDoc = this.GeneralDataAccessObject.getOne("teams", "_id", team.getIdx());
        if (teamDoc == null) {
            return List.of();
        }
        List<String> out = teamDoc.getList("users", String.class);
        if (out == null) {
            return List.of();
        }
        return out;
    }

    @Override
    public Team getTeam(String teamId) {
        Document teamDoc = GeneralDataAccessObject.getOne("teams", "_id", teamId);
        Team out = this.teamFactory.createFromDocument(teamDoc);
        ObjectId idx = teamDoc.getObjectId("_id");
        out.setIdx(idx.toString());
        return out;
    }

    @Override
    public User getUser(String userId) {
        Document user = this.GeneralDataAccessObject.getOne("users", "_id", userId);
        User out = this.userFactory.createFromDocument(user);
        ObjectId idx = user.getObjectId("_id");
        out.setIdx(idx.toString());
        return out;
    }
}
