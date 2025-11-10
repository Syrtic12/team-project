package entity;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import org.bson.Document;

//essentially tasklist for a team
public class Team {
    private String idx;
    private List<String> users;
    private TeamLeader leader;
    private List<Task> tasks;

    public Team(TeamLeader leader){
        this.idx = null; // to be set when added to database
        this.leader = leader;
        this.users = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Team other = (Team) obj;
        return this.idx != null && this.idx.equals(other.idx);
    }

    public String getIdx(){
        return this.idx;
    }

    public void setIdx(String idx){
        this.idx = idx;
    }

    public List<Task> GetTasks(){
        return this.tasks;
    }

    public void addTask(Task t){
        this.tasks.add(t);
    }

    public void removeTask(Task t){
        for (Task task : this.tasks) {
            if (task.equals(t)) {
                this.tasks.remove(task);
                break;
            }
        }
    }

    public JsonObject toJson(){
        JsonObject json = new JsonObject();
        json.addProperty("leader", this.leader.toJson().toString());
        JsonArray tasksJson = new JsonArray();
        for(Task task : this.tasks){
            tasksJson.add(task.toJson());
        }
        json.add("tasks", tasksJson);
        JsonArray usersJson = new JsonArray();
        for(String user : this.users){
            usersJson.add(user);
        }
        json.add("users", usersJson);
        return json;
    }

    public Document toDocument(){
        return Document.parse(this.toJson().toString());
    }
}
