package usecase.login;

import entity.User;

import java.util.List;

public interface LogInDataAccessInterface {
    /**
     * @param email
     * @return true if email already exists in the database, false otherwise
     */
    public boolean emailExists(String email);

    public User getUser(String email);

    public List<String> getTeams(String userID);
}
