package dataaccess;

import entity.Team;
import entity.TeamFactory;
import entity.User;
import entity.UserFactory;
import org.bson.Document;
import org.bson.types.ObjectId;
import usecase.leave_team.LeaveTeamDataAccessInterface;
import java.util.List;

public class LeaveTeamDataAccessObject implements LeaveTeamDataAccessInterface {
    private static final String TEAMS = "teams";
    private static final String USERS = "users";
    private static final String ID = "_id";
    private KandoMongoDatabase generalDataAccessObject;
    private final TeamFactory teamFactory = new TeamFactory();
    private final UserFactory userFactory = new UserFactory();


    public LeaveTeamDataAccessObject(KandoMongoDatabase dao) {
        generalDataAccessObject = dao;
    }

    @Override
    public Team getTeam(String teamId) {
        final Document teamDoc = generalDataAccessObject.getOne(TEAMS, ID, teamId);
        final Team out = this.teamFactory.createFromDocument(teamDoc);
        final ObjectId idx = teamDoc.getObjectId(ID);
        out.setIdx(idx.toString());
        return out;
    }

    @Override
    public User getUser(String userId) {
        final Document user = this.generalDataAccessObject.getOne(USERS, ID, userId);
        final User out = this.userFactory.createFromDocument(user);
        final ObjectId idx = user.getObjectId("_id");
        out.setIdx(idx.toString());
        return out;
    }

    @Override
    public List<String> getTeamMembers(Team team) {
        final Document teamDoc = this.generalDataAccessObject.getOne(TEAMS, ID, team.getIdx());
        List<String> out = List.of();
        if (teamDoc != null) {
            final List<String> members = teamDoc.getList(USERS, String.class);
            if (members != null) {
                out = members;
            }
        }
        return out;
    }

    @Override
    public String getTeamLeader(String team) {

        final Document leaderDoc = this.generalDataAccessObject.getOne(TEAMS, ID, team);
        return leaderDoc.getString("leader");
    }

    @Override
    public boolean removeMember(String teamId, String userId) {
        boolean result = false;
        final Document teamDoc = this.generalDataAccessObject.getOne(TEAMS, ID, teamId);
        final List<String> teamMembers = teamDoc.getList(USERS, String.class);

        if (userId != null && teamMembers.contains(userId)) {
            teamMembers.remove(userId);
            this.generalDataAccessObject.update(TEAMS, teamId, USERS, teamMembers);
            result = true;
        }
        return result;
    }

}
