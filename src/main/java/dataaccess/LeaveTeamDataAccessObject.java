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
    private KandoMongoDatabase GeneralDataAccessObject;
    private final TeamFactory teamFactory = new TeamFactory();
    private final UserFactory userFactory = new UserFactory();

    public LeaveTeamDataAccessObject(KandoMongoDatabase dao) {
        GeneralDataAccessObject = dao;
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
    public String getTeamLeader(String team) {

        Document leaderDoc = this.GeneralDataAccessObject.getOne("teams", "_id", team);
        String leaderId = leaderDoc.getString("leader");
        return leaderId;
    }

    @Override
    public boolean removeMember(String teamId, String userId) {
        Document teamDoc = this.GeneralDataAccessObject.getOne("teams", "_id", teamId);
        List<String> teamMembers = teamDoc.getList("users", String.class);

        if ((userId == null) || !teamMembers.contains(userId)) {
            return false;
        }
        teamMembers.remove(userId);
        this.GeneralDataAccessObject.update("teams", teamId, "users", teamMembers);
        return true;
    }


}
