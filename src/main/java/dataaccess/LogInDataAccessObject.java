package dataaccess;

import entity.Team;
import entity.User;
import entity.UserFactory;
import entity.TeamFactory;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import usecase.login.LogInDataAccessInterface;

public class LogInDataAccessObject implements LogInDataAccessInterface {
    private KandoMongoDatabase GeneralDataAccessObject;
    private final UserFactory userFactory;
    private final TeamFactory teamFactory;

    public LogInDataAccessObject(KandoMongoDatabase dao) {
        this.GeneralDataAccessObject = dao;
        this.userFactory = new UserFactory();
        this.teamFactory = new TeamFactory();
    }

    @Override
    public boolean emailExists(String email) {
        return GeneralDataAccessObject.emailExists(email);
    }

    @Override
    public User getUser(String email) {
        Document user = this.GeneralDataAccessObject.getOne("users", "email", email);
        User out = this.userFactory.createFromDocument(user);
        ObjectId idx = user.getObjectId("_id");
        out.setIdx(idx.toString());
        return out;
    }

    public List<String> getTeams(String userID) {
        List<Document> teamdocs = this.GeneralDataAccessObject.getMany("teams", "users", userID);
        List<String> teamslist = new ArrayList<>();
        for (Document teamdoc : teamdocs) {
            Team out = this.teamFactory.createFromDocument(teamdoc);
            teamslist.add(out.getIdx());
        }
        return teamslist;
    }
}
