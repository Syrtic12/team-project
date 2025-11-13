package use_case.login;

import entity.User;
import org.bson.Document;

public interface LogInDataAccessInterface {
    /**
     * @param email
     * @return true if email already exists in the database, false otherwise
     */
    public boolean emailExists(String email);

    public User getUser(String email);
}
