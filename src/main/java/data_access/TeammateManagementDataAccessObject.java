package data_access;

import java.util.List;
import entity.Team;
import entity.TeamFactory;
import entity.User;
import entity.UserFactory;
import org.bson.Document;
import org.bson.types.ObjectId;
import use_case.teammate_management.TeammateManagementDataAccessInterface;

public class TeammateManagementDataAccessObject implements TeammateManagementDataAccessInterface {
    private KandoMongoDatabase GeneralDataAccessObject;
    private final TeamFactory teamFactory = new TeamFactory();
    private final UserFactory userFactory = new UserFactory();
    private final String USERS_COLLECTION = "users";
    private final String TEAMS_COLLECTION = "teams";

    public TeammateManagementDataAccessObject(KandoMongoDatabase dao){ this.GeneralDataAccessObject = dao; }

    @Override
    public Team getTeam(String teamId) {
        Document teamDoc = GeneralDataAccessObject.getOne(TEAMS_COLLECTION, "_id", teamId);
        Team out = this.teamFactory.createFromDocument(teamDoc);
        ObjectId idx = teamDoc.getObjectId("_id");
        out.setIdx(idx.toString());
        return out;
    }

    @Override
    public User getUser(String email) {
        Document user = this.GeneralDataAccessObject.getOne(USERS_COLLECTION, "email", email);
        User out = this.userFactory.createFromDocument(user);
        ObjectId idx = user.getObjectId("_id");
        out.setIdx(idx.toString());
        return out;
    }

    @Override
    public User getUserFromId(String userId) {
        Document user = this.GeneralDataAccessObject.getOne("users", "_id", userId);
        User out = this.userFactory.createFromDocument(user);
        ObjectId idx = user.getObjectId("_id");
        out.setIdx(idx.toString());
        return out;
    }

    @Override
    public User getTeamLeader(Team team) {
        String leaderId = team.getLeaderIdx();
        Document leaderDoc = this.GeneralDataAccessObject.getOne(USERS_COLLECTION, "_id", leaderId);
        User out = this.userFactory.createFromDocument(leaderDoc);
        ObjectId idx = leaderDoc.getObjectId("_id");
        out.setIdx(idx.toString());
        return out;
    }

    @Override
    public List<String> getTeamMembers(Team team) {
        Document teamDoc = this.GeneralDataAccessObject.getOne(TEAMS_COLLECTION, "_id", team.getIdx());
        if (teamDoc == null) {
            return List.of();
        }
        List<String> out = teamDoc.getList(USERS_COLLECTION, String.class);
        if (out == null) {
            return List.of();
        }
        return out;
    }

    @Override
    public List<String> getTeams(User user) {
        Document userDoc = this.GeneralDataAccessObject.getOne(USERS_COLLECTION, "_id", user.getIdx());
        if (userDoc == null) {
            return List.of();
        }
        List<String> out = userDoc.getList(TEAMS_COLLECTION, String.class);
        if (out == null) {
            return List.of();
        }
        return out;
    }

    @Override
    public boolean addUser(Team team, User user) {
        List<String> teamMembers = getTeamMembers(team);
        if ((user.getIdx() == null)) {
            return false;
        }
        if (!teamMembers.contains(user.getIdx())) {
            teamMembers.add(user.getIdx());
            this.GeneralDataAccessObject.update(TEAMS_COLLECTION, team.getIdx(), USERS_COLLECTION, teamMembers);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeUser(Team team, User user) {
        List<String> teamMembers = getTeamMembers(team);
        if ((user.getIdx() == null) || !teamMembers.contains(user.getIdx())) {
            return false;
        }
        teamMembers.remove(user.getIdx());
        this.GeneralDataAccessObject.update(TEAMS_COLLECTION, team.getIdx(), USERS_COLLECTION, teamMembers);
        return true;
    }

    @Override
    public boolean addTeam(Team team, User user) {
        List<String> teams = getTeams(user);
        if ((user.getIdx() == null)) {
            return false;
        }
        if (!teams.contains(team.getIdx())) {
            teams.add(team.getIdx());
            this.GeneralDataAccessObject.update(USERS_COLLECTION, user.getIdx(), TEAMS_COLLECTION, teams);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeTeam(Team team, User user) {
        List<String> teams = getTeams(user);
        if ((team.getIdx() == null) || !teams.contains(team.getIdx())) {
            System.out.println("False");
            return false;
        }
        teams.remove(team.getIdx());
        this.GeneralDataAccessObject.update(USERS_COLLECTION, user.getIdx(), TEAMS_COLLECTION, teams);
        System.out.println("True");
        return true;
    }
}
