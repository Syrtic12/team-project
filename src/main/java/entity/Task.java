package entity;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.util.List;
import java.util.ArrayList;
import org.bson.Document;



public class Task {
    private String idx;
    private String title;
    private String description;
    private Integer status;
    private List<User> assignedUsers;

    public Task(String title, String description, Integer status, List<User> assignedUsers) {
        this.idx = null; // to be set when added to database
        this.title = title;
        this.description = description;
        this.status = status;
        this.assignedUsers = assignedUsers;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Task other = (Task) obj;
        return this.idx != null && this.idx.equals(other.idx);
    }

    public String getIdx(){
        return this.idx;
    }

    public void setIdx(String idx){
        this.idx = idx;
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

    public void assignUser(User user){
        this.assignedUsers.add(user);
    }
    public void unassignUser(){}
    public void changeStatus(){}



}
