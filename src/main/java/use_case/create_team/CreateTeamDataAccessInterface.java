package use_case.create_team;

public interface CreateTeamDataAccessInterface {
    /**
     * Checks whether the user already has a team with this name.
     * @param userId the user creating the team
     * @param teamName the team name to check
     * @return true if this team name is already used by the user
     */
    boolean teamNameExists(String userId, String teamName);

    /**
     * Saves a new team to the database.
     * @param userId the leader ID
     * @param teamName the team name
     */
    void saveTeam(String userId, String teamName);

    /**
     * Optional – get display name for output
     * @param userId leader ID
     * @return leader name
     */
    String getLeaderName(String userId);
}
