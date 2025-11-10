package data_access;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import entity.Team;
import entity.User;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;
import entity.Task;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class KandoMongoDatabase {
    private MongoDatabase database;

    public KandoMongoDatabase(){
        try {
            Dotenv dotenv = Dotenv.load();
            final String connectionString = String.format("mongodb+srv://sushaanpatel_db_user:%s@school.hyysls5.mongodb.net/&appName=school", dotenv.get("MONGOPASS"));
            System.out.println(connectionString);
            ServerApi serverApi = ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build();
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(connectionString))
                    .serverApi(serverApi)
                    .build();
            MongoClient mongoClient = MongoClients.create(settings);
            this.database = mongoClient.getDatabase("KandoDB");
            System.out.println("Connected to database successfully");
        } catch (MongoException e) {
            System.err.println(e.getMessage());
        }
    }

    public User add(User user){
        String idx = (this.database.getCollection("users").insertOne(user.toDocument())).getInsertedId().asObjectId().getValue().toString();
        user.setIdx(idx);
        return user;
    }

    public Task add(Task task){
        String idx = (this.database.getCollection("tasks").insertOne(task.toDocument())).getInsertedId().asObjectId().getValue().toString();
        task.setIdx(idx);
        return task;
    }

    public Team add(Team team){
        String idx = (this.database.getCollection("teams").insertOne(team.toDocument())).getInsertedId().asObjectId().getValue().toString();
        team.setIdx(idx);
        return team;
    }

    public boolean remove(Task task){
        Bson filter = Filters.eq("_id", new ObjectId(task.getIdx()));
        return this.database.getCollection("tasks").deleteOne(filter).wasAcknowledged();
    }

    public boolean remove(Team team){
        Bson filter = Filters.eq("_id", new ObjectId(team.getIdx()));
        return this.database.getCollection("teams").deleteOne(filter).wasAcknowledged();
    }

    public boolean remove(User user){
        Bson filter = Filters.eq("_id", new ObjectId(user.getIdx()));
        return this.database.getCollection("users").deleteOne(filter).wasAcknowledged();
    }

    public Document getOne(String idx, String collectionName){
        Bson filter = Filters.eq("_id", new ObjectId(idx));
        return this.database.getCollection(collectionName).find(filter).first();
    }

    public List<Document> getMany(String collectionName){
        List<Document> results = new ArrayList<>();
        for (Document doc : this.database.getCollection(collectionName).find()) {
            results.add(doc);
        }
        return results;
    }

    public List<Document> getMany(String collectionName, Bson filter){
        List<Document> results = new ArrayList<>();
        for (Document doc : this.database.getCollection(collectionName).find(filter)) {
            results.add(doc);
        }
        return results;
    }

    public static void main(String[] args) {
        KandoMongoDatabase kandoDB = new KandoMongoDatabase();
        Task task = new Task("Test Task 6", "This is a test task", 0, new ArrayList<>());
        task.setIdx("69124a5297f0eaf78cc49a0a");
        System.out.println(kandoDB.remove(task));



    }


}
