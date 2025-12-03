package dataaccess;

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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

/**
 * KandoMongoDatabase is a data access object for MongoDB.
 */
public class KandoMongoDatabase {
    private MongoDatabase database;
    private final String idL = "_id";
    private final String usersL = "users";

    /**
     * Constructor for KandoMongoDatabase.
     */
    public KandoMongoDatabase() {
        try {
            final Dotenv dotenv = Dotenv.load();
            final String connectionString = String.format(
                    "mongodb+srv://sushaanpatel_db_user:%s@school.hyysls5.mongodb.net/&appName=school",
                    dotenv.get("MONGOPASS"));
            final ServerApi serverApi = ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build();
            final MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(connectionString))
                    .serverApi(serverApi)
                    .build();
            final MongoClient mongoClient = MongoClients.create(settings);
            this.database = mongoClient.getDatabase("KandoDB");
            System.out.println("Connected to database successfully");
        }
        catch (MongoException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Function to add a user to the database.
     * @param user User to be added
     * @return User with assigned idx after being added to database
     */
    public User add(User user) {
        final String idx = (this.database.getCollection(usersL).insertOne(user.toDocument()))
                .getInsertedId().asObjectId().getValue().toString();
        user.setIdx(idx);
        return user;
    }

    /**
     * Function to add a task to the database.
     * @param task Task to be added
     * @return Task with assigned idx after being added to database
     */
    public Task add(Task task) {
        final String idx = (this.database.getCollection("tasks").insertOne(task.toDocument()))
                .getInsertedId().asObjectId().getValue().toString();
        task.setIdx(idx);
        return task;
    }

    /**
     * Function to add a team to the database.
     * @param team Team to be added
     * @return Team with assigned idx after being added to database
     */
    public Team add(Team team) {
        final String idx = (this.database.getCollection("teams").insertOne(team.toDocument()))
                .getInsertedId().asObjectId().getValue().toString();
        team.setIdx(idx);
        return team;
    }

    /**
     * Function to remove a task from the database.
     * @param task Task to be removed
     * @return boolean indicating if removal was acknowledged
     */
    public boolean remove(Task task) {
        final Bson filter = Filters.eq(idL, new ObjectId(task.getIdx()));
        return this.database.getCollection("tasks").deleteOne(filter).wasAcknowledged();
    }

    /**
     * Function to remove a team from the database.
     * @param team Team to be removed
     * @return boolean indicating if removal was acknowledged
     */
    public boolean remove(Team team) {
        final Bson filter = Filters.eq(idL, new ObjectId(team.getIdx()));
        return this.database.getCollection("teams").deleteOne(filter).wasAcknowledged();
    }

    /**
     * Function to remove a user from the database.
     * @param user User to be removed
     * @return boolean indicating if removal was acknowledged
     */
    public boolean remove(User user) {
        final Bson filter = Filters.eq(idL, new ObjectId(user.getIdx()));
        return this.database.getCollection(usersL).deleteOne(filter).wasAcknowledged();
    }

    /**
     * Function to get one document from the database.
     * @param collectionName collection from which to retrieve the document
     * @param key key to filter by
     * @param value value to filter by
     * @return Document with given idx from specified collection
     */
    public Document getOne(String collectionName, String key, String value) {
        final Bson filter;
        if (key.equals(idL)) {
            filter = Filters.eq(key, new ObjectId(value));
        }
        else {
            filter = Filters.eq(key, value);
        }
        return this.database.getCollection(collectionName).find(filter).first();
    }

    /**
     * Function to get all documents from a collection.
     * @param collectionName collection from which to retrieve documents
     * @return List of all Documents in specified collection
     */
    public List<Document> getAll(String collectionName) {
        final List<Document> results = new ArrayList<>();
        for (Document doc : this.database.getCollection(collectionName).find()) {
            results.add(doc);
        }
        return results;
    }

    /**
     * Gets all documents from the specified collection matching the given filter.
     * @param collectionName collection from which to retrieve documents
     * @param key key to filter by
     * @param value value to filter by
     * @return List of Documents in specified collection matching filter
     */
    public List<Document> getMany(String collectionName, String key, String value) {
        final Bson filter;
        if (key.equals(idL)) {
            filter = Filters.eq(key, new ObjectId(value));
        }
        else {
            filter = Filters.eq(key, value);
        }
        final List<Document> results = new ArrayList<>();
        for (Document doc : this.database.getCollection(collectionName).find(filter)) {
            results.add(doc);
        }
        return results;
    }

    /**
     * Updates a document's key to the specified value in the given collection.
     * @param collectionName collection in which to update document
     * @param idx idx of document to be updated
     * @param key key to be updated
     * @param value value to set key to
     */
    public void update(String collectionName, String idx, String key, String value) {
        this.database.getCollection(collectionName).updateOne(Filters.eq(idL, new ObjectId(idx)),
                Updates.set(key, value));
    }
    
    /**
     * Updates a document's key to the specified value in the given collection.
     * @param collectionName collection in which to update document
     * @param idx idx of document to be updated
     * @param key key to be updated
     * @param value value to set key to
     */
    public void update(String collectionName, String idx, String key, List<?> value) {
        this.database.getCollection(collectionName).updateOne(Filters.eq(idL, new ObjectId(idx)),
                Updates.set(key, value));
    }

    /**
     * Updates a document's status to the specified value in the given collection.
     * @param collectionName collection in which to update document
     * @param idx idx of document to be updated
     * @param status status value to set
     */
    public void updateStatus(String collectionName, String idx, Integer status) {
        this.database.getCollection(collectionName).updateOne(Filters.eq(idL, new ObjectId(idx)),
                Updates.set("status", status));
    }

    /**
     * Checks if an email exists in the users collection.
     * @param email email to check for existence
     * @return boolean indicating if email exists in users collection
     */
    public boolean emailExists(String email) {
        final Bson filter = Filters.eq("email", email);
        final Document userDoc = this.database.getCollection(usersL).find(filter).first();
        return userDoc != null;
    }
}
