package dataaccess;

import entity.Team;
import entity.TeamFactory;
import entity.User;
import entity.UserFactory;
import org.bson.Document;
import org.bson.types.ObjectId;
import usecase.login.LogInDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for login-related operations.
 */
public class LogInDataAccessObject implements LogInDataAccessInterface {
    private final KandoMongoDatabase generalDataAccessObject;
    private final UserFactory userFactory;
    private final TeamFactory teamFactory;

    public LogInDataAccessObject(KandoMongoDatabase dao) {
        this.generalDataAccessObject = dao;
        this.userFactory = new UserFactory();
        this.teamFactory = new TeamFactory();
    }

    @Override
    public boolean emailExists(String email) {
        return generalDataAccessObject.emailExists(email);
    }

    @Override
    public User getUser(String email) {
        final Document user = this.generalDataAccessObject.getOne("users", "email", email);
        final User out = this.userFactory.createFromDocument(user);
        final ObjectId idx = user.getObjectId("_id");
        out.setIdx(idx.toString());
        return out;
    }

    /**
     * Get a list of team IDs that the user is a member of.
     * @param userId the user's ID
     * @return a list of team IDs that the user is a member of
     */
    public List<String> getTeams(String userId) {
        final List<Document> teamdocs = this.generalDataAccessObject.getMany("teams", "users", userId);
        final List<String> teamslist = new ArrayList<>();
        for (Document teamdoc : teamdocs) {
            final Team out = this.teamFactory.createFromDocument(teamdoc);
            teamslist.add(out.getIdx());
        }
        return teamslist;
    }
}
