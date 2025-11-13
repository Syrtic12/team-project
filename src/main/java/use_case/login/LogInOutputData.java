package use_case.login;

public class LogInOutputData {
    private final String email;
    private final String idx;

    public LogInOutputData(String idx, String email) {
        this.idx = idx;
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public String getIdx() {
        return this.idx;
    }
}
