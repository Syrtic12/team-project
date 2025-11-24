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
    private String leaderIdx;
    private List<String> tasks;

    /**
     * @param leaderIdx id of leader of the team
     * Constructor for Team
     */
    public Team(String leaderIdx){
        this.idx = null; // to be set when added to database
        this.leaderIdx = leaderIdx;
        this.users = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    /**
     * @param obj the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Team other = (Team) obj;
        return this.idx != null && this.idx.equals(other.idx);
    }

    /**
     * @return idx of this Team
     */
    public String getIdx(){
        return this.idx;
    }

    /**
     * @return the TeamLeader object for this Team
     */
    public TeamLeader getLeader() {
        return this.leader;
    }


    /**
     * @param idx idx of this Team
     * Set the idx of this Team
     */
    public void setIdx(String idx){
        this.idx = idx;
    }

    /**
     * @return id of team leader
     */
    public String getLeaderIdx(){
        return this.leaderIdx;
    }

    /**
     * @return list of ids of the team's tasks
     */
    public List<String> getTasks(){
        return this.tasks;
    }

    /**
     * @param task task id to be added
     * Add a task id to the team's task list
     */
    public void addTask(String task){
        this.tasks.add(task);
    }

    /**
     * @param user user id to be added
     * Add a user id to the team's task list
     */
    public void addUser(String user){this.users.add(user);}

    /**
     * @param t
     * Remove a task t's id from the team's task list
     */
    public void removeTask(Task t){
        for (String task : this.tasks) {
            if (task.equals(t.getIdx())) {
                this.tasks.remove(task);
                break;
            }
        }
    }

    public void addUser(String user){this.users.add(user);}

    /**
     * @return Json representation of this Team
     */
    public JsonObject toJson(){
        JsonObject json = new JsonObject();
        json.addProperty("leader", this.leaderIdx);
        JsonArray tasksJson = new JsonArray();
        for(String task : this.tasks){
            tasksJson.add(task);
        }
        json.add("tasks", tasksJson);
        JsonArray usersJson = new JsonArray();
        for(String user : this.users){
            usersJson.add(user);
        }
        json.add("users", usersJson);
        return json;
    }

    /**
     * @return Document representation of this Team
     */
    public Document toDocument(){
        return Document.parse(this.toJson().toString());
    }
}