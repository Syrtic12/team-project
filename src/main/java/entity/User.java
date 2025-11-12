package entity;

import com.google.gson.JsonObject;

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

    public JsonObject toJson() {return new JsonObject();}

}
