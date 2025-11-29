package use_case.create_team;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;

public class CreateTeamDataAccessObject implements CreateTeamDataAccessInterface {

    private final MongoCollection<Document> teamsCollection;

    public CreateTeamDataAccessObject(MongoDatabase database) {
        this.teamsCollection = database.getCollection("teams");
    }

    @Override
    public boolean teamNameExists(String userId, String teamName) {
        Document result = teamsCollection.find(
                and(
                        eq("userId", userId),
                        eq("teamName", teamName)
                )
        ).first();
        return result != null;
    }

    @Override
    public void saveTeam(String userId, String teamName) {
        Document doc = new Document();
        doc.put("userId", userId);
        doc.put("teamName", teamName);
        doc.put("leaderName", "");
        teamsCollection.insertOne(doc);
    }


    @Override
    public String getLeaderName(String userId) {
        Document doc = teamsCollection.find(eq("userId", userId)).first();
        if (doc == null) {
            return "";
        }
        return doc.getString("leaderName");
    }

    @Override
    public void deleteTeam(String userId, String teamName) {
        teamsCollection.deleteOne(
                and(
                        eq("userId", userId),
                        eq("teamName", teamName)
                )
        );
    }
}

