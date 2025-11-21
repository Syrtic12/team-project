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
import com.mongodb.client.model.Updates;
import entity.Team;
import entity.User;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;
import entity.Task;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import use_case.signup.SignUpDataAccessInterface;

import java.util.ArrayList;
import java.util.List;


public class KandoMongoDatabase {
    private MongoDatabase database;

    /**
     * Constructor for KandoMongoDatabase
     */
    public KandoMongoDatabase(){
        try {
            Dotenv dotenv = Dotenv.load();
            final String connectionString = String.format("mongodb+srv://sushaanpatel_db_user:%s@school.hyysls5.mongodb.net/&appName=school", dotenv.get("MONGOPASS"));
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

    /**
     * @param user User to be added
     * @return User with assigned idx after being added to database
     */
    public User add(User user){
        String idx = (this.database.getCollection("users").insertOne(user.toDocument())).getInsertedId().asObjectId().getValue().toString();
        user.setIdx(idx);
        return user;
    }

    /**
     * @param task Task to be added
     * @return Task with assigned idx after being added to database
     */
    public Task add(Task task){
        String idx = (this.database.getCollection("tasks").insertOne(task.toDocument())).getInsertedId().asObjectId().getValue().toString();
        task.setIdx(idx);
        return task;
    }

    /**
     * @param team Team to be added
     * @return Team with assigned idx after being added to database
     */
    public Team add(Team team){
        String idx = (this.database.getCollection("teams").insertOne(team.toDocument())).getInsertedId().asObjectId().getValue().toString();
        team.setIdx(idx);
        return team;
    }

    /**
     * @param task Task to be removed
     * @return boolean indicating if removal was acknowledged
     */
    public boolean remove(Task task){
        Bson filter = Filters.eq("_id", new ObjectId(task.getIdx()));
        return this.database.getCollection("tasks").deleteOne(filter).wasAcknowledged();
    }

    /**
     * @param team Team to be removed
     * @return boolean indicating if removal was acknowledged
     */
    public boolean remove(Team team){
        Bson filter = Filters.eq("_id", new ObjectId(team.getIdx()));
        return this.database.getCollection("teams").deleteOne(filter).wasAcknowledged();
    }

    /**
     * @param user User to be removed
     * @return boolean indicating if removal was acknowledged
     */
    public boolean remove(User user){
        Bson filter = Filters.eq("_id", new ObjectId(user.getIdx()));
        return this.database.getCollection("users").deleteOne(filter).wasAcknowledged();
    }

    /**
     * @param collectionName collection from which to retrieve the document
     * @param key key to filter by
     * @param value value to filter by
     * @return Document with given idx from specified collection
     * Gets one document from the specified collection by its idx
     */
    public Document getOne(String collectionName, String key, String value){
        Bson filter;
        if (key.equals("_id")) {
            filter = Filters.eq(key, new ObjectId(value));
        } else {
            filter = Filters.eq(key, value);
        }
        return this.database.getCollection(collectionName).find(filter).first();
    }

    /**
     * @param collectionName collection from which to retrieve documents
     * @return List of all Documents in specified collection
     * Gets all documents from the specified collection
     */
    public List<Document> getAll(String collectionName){
        List<Document> results = new ArrayList<>();
        for (Document doc : this.database.getCollection(collectionName).find()) {
            results.add(doc);
        }
        return results;
    }

    /**
     * @param collectionName collection from which to retrieve documents
     * @param key key to filter by
     * @param value value to filter by
     * @return List of Documents in specified collection matching filter
     * Gets all documents from the specified collection matching the given filter
     */
    public List<Document> getMany(String collectionName, String key, String value){
        Bson filter;
        if (key.equals("_id")) {
            filter = Filters.eq(key, new ObjectId(value));
        } else {
            filter = Filters.eq(key, value);
        }
        List<Document> results = new ArrayList<>();
        for (Document doc : this.database.getCollection(collectionName).find(filter)) {
            results.add(doc);
        }
        return results;
    }

    /**
     * @param collectionName collection in which to update document
     * @param idx idx of document to be updated
     * @param key key to be updated
     * @param value value to set key to
     * Updates a document's key to the specified value in the given collection
     */
    public void update(String collectionName, String idx, String key, List<?> value){
        this.database.getCollection(collectionName).updateOne(Filters.eq("_id", new ObjectId(idx)), Updates.set(key, value));
    }

    /**
     * @param collectionName collection in which to update document
     * @param idx idx of document to be updated
     * @param status status value to set
     * Updates a document's status to the specified value in the given collection
     */
    public void updateStatus(String collectionName, String idx, Integer status){
        this.database.getCollection(collectionName).updateOne(Filters.eq("_id", new ObjectId(idx)), Updates.set("status", status));
    }

    /**
     * @param email email to check for existence
     * @return boolean indicating if email exists in users collection
     */
    public boolean emailExists(String email) {
        Bson filter = Filters.eq("email", email);
        Document userDoc = this.database.getCollection("users").find(filter).first();
        return userDoc != null;
    }

    public static void main(String[] args) {
        KandoMongoDatabase kandoDB = new KandoMongoDatabase();
//        kandoDB.update("tasks", "6911237d1102171d2e1fac90", "title", "Test Task Updated 1");
//        kandoDB.updateStatus("tasks", "6911237d1102171d2e1fac90", 2);
        System.out.println(kandoDB.getOne("users", "email", "sushaanpatel@gmail.com"));


    }


}
