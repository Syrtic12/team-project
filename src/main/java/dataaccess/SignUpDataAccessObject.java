package dataaccess;

import entity.User;
import usecase.signup.SignUpDataAccessInterface;

/**
 * Data Access Object for handling user sign-up related database operations.
 */
public class SignUpDataAccessObject implements SignUpDataAccessInterface {
    private final KandoMongoDatabase generalDataAccessObject;

    public SignUpDataAccessObject(KandoMongoDatabase dao) {
        this.generalDataAccessObject = dao;
    }

    /**
     * Adds a new user to the database.
     * @param user User to be added
     * @return the added User
     */
    public User addUser(User user) {
        return generalDataAccessObject.add(user);
    }

    /**
     * Checks if an email already exists in the database.
     * @param email Email to be checked
     * @return true if email exists, false otherwise
     */
    public boolean emailExists(String email) {
        return generalDataAccessObject.emailExists(email);
    }
}
