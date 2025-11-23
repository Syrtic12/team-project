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
    private List<String> assignedUsers;

    /**
     * @param title
     * @param description
     * @param status
     */
    public Task(String title, String description, Integer status) {
        this.idx = null; // to be set when added to database
        this.title = title;
        this.description = description;
        this.status = status;
        this.assignedUsers = new ArrayList<>();
    }

    /**
     * @param obj the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Task other = (Task) obj;
        return this.idx != null && this.idx.equals(other.idx);
    }

    /**
     * @return String idx of this Task
     */
    public String getIdx(){
        return this.idx;
    }

    /**
     * @param idx
     * Set the idx of this Task
     */
    public void setIdx(String idx){
        this.idx = idx;
    }

    /**
     * @return Json representation of this Task
     */
    public JsonObject toJson(){
        JsonObject json = new JsonObject();
        json.addProperty("title", this.title);
        json.addProperty("description", this.description);
        json.addProperty("status", this.status);

        JsonArray usersJson = new JsonArray();
        for(String user : this.assignedUsers){
            usersJson.add(user);
        }
        json.add("users", usersJson);
        return json;
    }

    /**
     * @return Document representation of this Task
     */
    public Document toDocument(){
        return Document.parse(this.toJson().toString());
    }

    /**
     * @param user
     * Assign a user to this task
     */
    public void assignUser(String user){
        this.assignedUsers.add(user);
    }

    /**
     * @param user
     * Unassign a user from this task
     */
    public void unassignUser(String user){
        for(String u : this.assignedUsers){
            if(u.equals(user)){
                this.assignedUsers.remove(u);
                break;
            }
        }
    }

    /**
     * @param newStatus
     * Change the status of this task
     */
    public void changeStatus(Integer newStatus){
        this.status = newStatus;
    }

    public String getTitle() {
    return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getStatus() {
        return this.status;
    }

    public List<String> getAssignedUsers() {
        return this.assignedUsers;
    }

    public boolean isAssignedUser(String invokedBy) {
        throw new UnsupportedOperationException("Unimplemented method 'isAssignedUser'");
    }

    public void setTitle(String newTitle) {
        throw new UnsupportedOperationException("Unimplemented method 'setTitle'");
    }

    public void setDescription(String newDescription) {
        throw new UnsupportedOperationException("Unimplemented method 'setDescription'");
    }




}
