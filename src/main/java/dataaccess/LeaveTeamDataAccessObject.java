package dataaccess;

import entity.Team;
import entity.TeamFactory;
import entity.User;
import entity.UserFactory;
import org.bson.Document;
import org.bson.types.ObjectId;
import usecase.leave_team.LeaveTeamDataAccessInterface;
import java.util.List;

/**
 * Data access object for the Leave Team use case.
 * Provides methods to retrieve team and user information from MongoDB and
 * update team membership when a user leaves a team.
 */
public class LeaveTeamDataAccessObject implements LeaveTeamDataAccessInterface {
    private static final String TEAMS = "teams";
    private static final String USERS = "users";
    private static final String ID = "_id";
    private final KandoMongoDatabase generalDataAccessObject;
    private final TeamFactory teamFactory = new TeamFactory();
    private final UserFactory userFactory = new UserFactory();

    /**
     * Constructs the data access object with the provided database wrapper.
     *
     * @param dao the database interface used to interact with MongoDB collections
     */
    public LeaveTeamDataAccessObject(KandoMongoDatabase dao) {
        generalDataAccessObject = dao;
    }

    /**
     * Retrieves a team by its ID and converts the MongoDB document into a Team entity.
     *
     * @param teamId the ID of the team
     * @return the Team entity corresponding to the given ID
     */
    @Override
    public Team getTeam(String teamId) {
        final Document teamDoc = generalDataAccessObject.getOne(TEAMS, ID, teamId);
        final Team out = this.teamFactory.createFromDocument(teamDoc);
        final ObjectId idx = teamDoc.getObjectId(ID);
        out.setIdx(idx.toString());
        return out;
    }

    /**
     * Retrieves a user by ID and converts the MongoDB document into a User entity.
     *
     * @param userId the ID of the user
     * @return the User entity corresponding to the given ID
     */
    @Override
    public User getUser(String userId) {
        final Document user = this.generalDataAccessObject.getOne(USERS, ID, userId);
        final User out = this.userFactory.createFromDocument(user);
        final ObjectId idx = user.getObjectId("_id");
        out.setIdx(idx.toString());
        return out;
    }

    /**
     * Retrieves the list of user IDs belonging to a specific team.
     *
     * @param team the team whose members should be retrieved
     * @return a list of user IDs belonging to the team
     */
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

    /**
     * Retrieves the leader ID for a given team.
     *
     * @param team the ID of the team
     * @return the user ID of the team leader
     */
    @Override
    public String getTeamLeader(String team) {
        final Document leaderDoc = this.generalDataAccessObject.getOne(TEAMS, ID, team);
        return leaderDoc.getString("leader");
    }

    /**
     * Removes a user from a team's member list and updates the stored team document.
     *
     * @param teamId the ID of the team
     * @param userId the ID of the user to remove
     * @return true if the user was removed, false otherwise
     */
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
