package data_access;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import io.github.cdimascio.dotenv.Dotenv;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import org.bson.Document;
import entity.Task;

import java.util.ArrayList;

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

    public void addUser(Document user){
        this.database.getCollection("users").insertOne(user);
    }

    public void addTask(Document task){
        this.database.getCollection("tasks").insertOne(task);
    }

    public static void main(String[] args) {
        KandoMongoDatabase kandoDB = new KandoMongoDatabase();
        Task task = new Task("Test Task", "This is a test task", 0, new ArrayList<>());
        kandoDB.addTask(task.toDocument());
    }


}
