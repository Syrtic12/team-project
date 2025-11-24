package interface_adapter.logged_in;

import java.util.ArrayList;
import java.util.List;

public class LoggedInState {
    private String email = "";
    private String password = "";
    private String passwordError;
    private List<String> teams =  new ArrayList<>();

    public LoggedInState (LoggedInState copy) {
        email = copy.email;
        password = copy.password;
        passwordError = copy.passwordError;
        teams = new ArrayList<>(copy.teams);
    }

    public LoggedInState() {
    }

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
}
