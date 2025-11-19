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
        return List.of();
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
//        this.GeneralDataAccessObject.update("team", team.getIdx(), "users", );
        return true;
    }


}
