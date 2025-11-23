package entity;

import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.List;

public class TeamFactory {

    private final UserFactory userFactory;

    public TeamFactory() {
        userFactory = new UserFactory();
    }

    public Team create(TeamLeader leader) {
        return new Team(leader);
    }

    public Team createFromDocument(Document document) {
        Document leaderDoc = document.get("leader", Document.class);
        User user = userFactory.createFromDocument(leaderDoc);
        TeamLeader leader = (TeamLeader) user;
        Team team = new Team(leader);

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
