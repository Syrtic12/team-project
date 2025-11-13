package entity;

import org.bson.Document;

public class UserFactory {

    public User create(String name, String email, String password, String role) {
        if (role.equals("Leader")) {
            return new TeamLeader(name, email, role, password);
        } else if (role.equals("Member")) {
            return new TeamMember(name, email, role, password);
        } else {
            return new User(name, email, role, password);
        }
    }

    public User createFromDocument(Document user) {
        System.out.println(user.getString("email"));
        return new User(user.getString("name"), user.getString("email"), user.getString("role"), user.getString("password"));
    }
}
