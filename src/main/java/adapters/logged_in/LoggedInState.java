package adapters.logged_in;

import java.util.ArrayList;
import java.util.List;

public class LoggedInState {

    private String email = "";
    private String password = "";
    private String passwordError;
    private List<String> teams = new ArrayList<>();
    private String userId;

    public LoggedInState(LoggedInState copy) {
        this.email = copy.email;
        this.password = copy.password;
        this.passwordError = copy.passwordError;
        this.teams = new ArrayList<>(copy.teams);
        this.userId = copy.userId;
    }

    public LoggedInState() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public List<String> getTeams() {
        return teams;
    }

    public void setTeams(List<String> teams) {
        this.teams = teams;
    }

    public void addTeam(String teamName) {
        teams.add(teamName);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
