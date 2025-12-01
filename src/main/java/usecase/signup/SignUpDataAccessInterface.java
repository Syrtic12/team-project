package usecase.signup;

import entity.User;

/**
 * The SignUpDataAccessInterface defines the methods for accessing user data during the sign-up process.
 */
public interface SignUpDataAccessInterface {

    /**
     * Summary: Adds a new user to the database.
     * @param user User to be added
     * @return User with assigned idx after being added to database
     */
    User addUser(User user);

    /**
     * Summary: Checks if an email already exists in the database.
     * @param email Email to be checked
     * @return true if email already exists in the database, false otherwise
     */
    boolean emailExists(String email);
}
