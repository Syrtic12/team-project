package use_case.signup;

import entity.User;

public interface SignUpDataAccessInterface {

    /**
     * @param user User to be added
     * @return User with assigned idx after being added to database
     */
    User add(User user);

    boolean emailExists(String email);
}
