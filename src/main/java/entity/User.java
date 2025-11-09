package entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String email;
    private String role;
    private List<Team> teams;
    private String password;

    public User(String name, String email, String role, String password) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
        this.teams = new ArrayList<>();
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    public void removeTeam(Team team) {
        this.teams.remove(team);
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
