package entity;

import org.bson.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class UserFactory {

    public User create(String name, String email, String password, String role) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String temp = encoder.encode(password);
        if (role.equals("Leader")) {
            return new TeamLeader(name, email, role, temp);
        } else if (role.equals("Member")) {
            return new TeamMember(name, email, role, temp);
        } else {
            return new User(name, email, role, temp);
        }
    }

    public User createFromDocument(Document user) {
        return new User(user.getString("name"), user.getString("email"), user.getString("role"), user.getString("password"));
    }
}
