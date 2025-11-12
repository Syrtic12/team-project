package entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String idx;
    private String name;
    private String email;
    private String role;
    private List<String> teams; // maybe store team IDs instead
    private String password;

    /**
     * @param name
     * @param email
     * @param role
     * @param password
     * Constructor for User
     */
    public User(String name, String email, String role, String password) {
        this.idx = null; // to be set when added to database
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
        this.teams = new ArrayList<>();
    }

    /**
     * @param obj the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User other = (User) obj;
        return this.idx != null && this.idx.equals(other.idx);
    }

    /**
     * @return idx of this User
     */
    public String getIdx(){
        return this.idx;
    }

    /**
     * @param idx
     * Set the idx of this User
     */
    public void setIdx(String idx){
        this.idx = idx;
    }

    /**
     * @return email of this User
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @param team
     * Add a team's id to this User's list of teams
     */
    public void addTeam(String team) {
        this.teams.add(team);
    }

    /**
     * @param team
     * Remove a team's id from this User's list of teams
     */
    public void removeTeam(Team team) {
        for (String t : this.teams) {
            if (t.equals(team.getIdx())) {
                this.teams.remove(t);
                break;
            }
        }
    }

    /**
     * @return Json representation of this User
     */
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("name", this.name);
        json.addProperty("email", this.email);
        json.addProperty("role", this.role);

        JsonArray usersJson = new JsonArray();
        for (String team : this.teams) {
            usersJson.add(team);
        }
        json.add("teams", usersJson);
        return json;
    }

    /**
     * @return Document representation of this User
     */
    public Document toDocument(){
        return Document.parse(this.toJson().toString());
    }

}
