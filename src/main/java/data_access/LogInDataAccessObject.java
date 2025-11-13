package data_access;

import entity.User;
import entity.UserFactory;
import org.bson.Document;
import org.bson.types.ObjectId;
import use_case.login.LogInDataAccessInterface;

public class LogInDataAccessObject implements LogInDataAccessInterface {
    private KandoMongoDatabase GeneralDataAccessObject;
    private final UserFactory userFactory;

    public LogInDataAccessObject(KandoMongoDatabase dao) {
        this.GeneralDataAccessObject = dao;
        this.userFactory = new UserFactory();
    }

    @Override
    public boolean emailExists(String email) {
        return GeneralDataAccessObject.emailExists(email);
    }

    @Override
    public User getUser(String email) {
        Document user = this.GeneralDataAccessObject.getOne("users", "email", email);
        User out = this.userFactory.createFromDocument(user);
        ObjectId idx = user.getObjectId("_id");
        out.setIdx(idx.toString());
        return out;
    }
}
