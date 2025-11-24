package entity;

import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.List;

public class TeamFactory {

    private final UserFactory userFactory;

    public TeamFactory() {
        userFactory = new UserFactory();
    }

    public Team create(String leaderID) {
        return new Team(leaderID);
    }

    public Team createFromDocument(Document document) {
        String leaderID = document.getString("leader");
        Team team = new Team(leaderID);


        team.setIdx(document.getObjectId("_id").toString());

        List<String> users = document.getList("users", String.class);
        if (users != null) {
            for (String userId : users) {
                team.addUser(userId);
            }
        }

        List<String> tasks = document.getList("tasks", String.class);
        if (tasks != null) {
            for (String task : tasks) {
                team.addTask(task);
            }
        }

        return team;
    }
}
