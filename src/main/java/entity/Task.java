package entity;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.util.List;
import java.util.ArrayList;
import org.bson.Document;



public class Task {
    private String title;
    private String description;
    private Integer status;
    private List<User> assignedUsers;

    public Task(String title, String description, Integer status, List<User> assignedUsers) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.assignedUsers = assignedUsers;
    }

    public JsonObject toJson(){
        JsonObject json = new JsonObject();
        json.addProperty("title", this.title);
        json.addProperty("description", this.description);
        json.addProperty("status", this.status);

        JsonArray usersJson = new JsonArray();
        for(User user : this.assignedUsers){
            usersJson.add(user.toJson());
        }
        json.add("users", usersJson);
        return json;
    }

    public Document toDocument(){
        return Document.parse(this.toJson().toString());
    }

    public void assignUser(){}
    public void unassignUser(){}
    public void changeStatus(){}



}
