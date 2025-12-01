package dataaccess;

import entity.User;
import usecase.signup.SignUpDataAccessInterface;

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
