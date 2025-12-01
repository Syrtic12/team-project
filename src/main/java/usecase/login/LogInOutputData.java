package usecase.login;

import java.util.List;

public class LogInOutputData {
    private final String email;
    private final String idx;
    private final List<String> teams;

    public LogInOutputData(String idx, String email, List<String> teams) {
        this.idx = idx;
        this.email = email;
        this.teams = teams;
    }

    public String getEmail() {
        return this.email;
    }

    public String getIdx() {
        return this.idx;
    }

    public List<String> getTeams() {return this.teams;}
}
