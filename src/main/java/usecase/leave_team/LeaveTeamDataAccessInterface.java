package usecase.leave_team;

import entity.Team;
import entity.User;
import java.util.List;

/**
 * Interface defining the data access operations required for the Leave Team use case.
 * Implementations provide persistence-layer interactions for retrieving teams,
 * users, team membership, leaders, and updating membership when users leave teams.
 */
public interface LeaveTeamDataAccessInterface {

    /**
     * Retrieves a team by its ID.
     *
     * @param teamId the ID of the team
     * @return the Team entity for the given ID
     */
    Team getTeam(String teamId);

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user
     * @return the User entity for the given ID
     */
    User getUser(String userId);

    /**
     * Retrieves the list of user IDs belonging to the given team.
     *
     * @param team the team whose members are requested
     * @return a list of user IDs
     */
    List<String> getTeamMembers(Team team);

    /**
     * Retrieves the leader ID of the specified team.
     *
     * @param teamid the ID of the team
     * @return the user ID of the team leader
     */
    String getTeamLeader(String teamid);

    /**
     * Removes a user from the specified team.
     *
     * @param teamId the team to update
     * @param userId the user ID to remove
     * @return true if removal succeeded, false otherwise
     */
    boolean removeMember(String teamId, String userId);
}
