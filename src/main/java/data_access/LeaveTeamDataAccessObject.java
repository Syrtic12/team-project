package data_access;

import entity.Team;
import entity.TeamFactory;
import entity.User;
import entity.UserFactory;
import org.bson.Document;
import org.bson.types.ObjectId;
import use_case.leave_team.LeaveTeamDataAccessInterface;
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
    public User getTeamLeader(Team team) {
        Document leaderDoc = this.GeneralDataAccessObject.getOne("teams", "leader", team.getIdx());
        User out = this.userFactory.createFromDocument(leaderDoc);
        ObjectId idx = leaderDoc.getObjectId("_id");
        out.setIdx(idx.toString());
        return out;
    }

    @Override
    public boolean removeMember(Team team, User user) {
        List<String> teamMembers = getTeamMembers(team);
        if ((user.getIdx() == null) || !teamMembers.contains(user.getIdx())) {
            return false;
        }
        teamMembers.remove(user.getIdx());
        this.GeneralDataAccessObject.update("teams", "users", team.getIdx(), teamMembers);
        return true;
    }


}
