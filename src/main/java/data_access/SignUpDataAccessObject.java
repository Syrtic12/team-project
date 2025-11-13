package data_access;

import entity.User;
import use_case.signup.SignUpDataAccessInterface;

public class SignUpDataAccessObject implements SignUpDataAccessInterface {
    private KandoMongoDatabase GeneralDataAccessObject;

    public SignUpDataAccessObject(KandoMongoDatabase dao) {
        this.GeneralDataAccessObject = dao;
    }

    public User addUser(User user){
        return GeneralDataAccessObject.add(user);
    }

    public boolean emailExists(String email){
        return GeneralDataAccessObject.emailExists(email);
    }
}
