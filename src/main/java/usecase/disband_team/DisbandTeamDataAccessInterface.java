package usecase.disband_team;

public interface DisbandTeamDataAccessInterface {

    /**
     * Check if the team exists.
     */
    boolean teamExists(String userId, String teamId);

    /**
     * Remove the team and all member associations.
     */
    void deleteTeam(String teamId);
}
