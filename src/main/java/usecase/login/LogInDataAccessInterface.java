package usecase.login;

import entity.User;

import java.util.List;

/**
 * The data access interface for the LogIn Use Case.
 */
public interface LogInDataAccessInterface {
    /**
     * Checks if an email already exists in the database.
     * @param email the email to check
     * @return true if email already exists in the database, false otherwise
     */
    boolean emailExists(String email);

    /**
     * Retrieves the User object associated with the given email.
     * @param email the email of the user
     * @return the User object associated with the given email
     */
    User getUser(String email);

    /**
     * Retrieves the list of teams associated with the given user ID.
     * @param userId the ID of the user
     * @return the list of teams associated with the given user ID
     */
    List<String> getTeams(String userId);
}
