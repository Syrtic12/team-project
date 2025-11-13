package use_case.signup;

import entity.User;

public interface SignUpDataAccessInterface {

    /**
     * @param user User to be added
     * @return User with assigned idx after being added to database
     */
    public User addUser(User user);

    /**
     * @param email
     * @return true if email already exists in the database, false otherwise
     */
    public boolean emailExists(String email);
}
