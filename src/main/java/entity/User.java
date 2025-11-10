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
    private List<Team> teams;
    private String password;

    public User(String name, String email, String role, String password) {
        this.idx = null; // to be set when added to database
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
        this.teams = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User other = (User) obj;
        return this.idx != null && this.idx.equals(other.idx);
    }

    public String getIdx(){
        return this.idx;
    }

    public void setIdx(String idx){
        this.idx = idx;
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    public void removeTeam(Team team) {
        for (Team t : this.teams) {
            if (t.equals(team)) {
                this.teams.remove(t);
                break;
            }
        }
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("name", this.name);
        json.addProperty("email", this.email);
        json.addProperty("role", this.role);

        JsonArray usersJson = new JsonArray();
        for (Team team : this.teams) {
            usersJson.add(team.toJson());
        }
        json.add("teams", usersJson);
        return json;
    }

    public Document toDocument(){
        return Document.parse(this.toJson().toString());
    }

}
