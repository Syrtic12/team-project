package use_case.login;

public class LogInInputData {
    private final String email;
    private final String password;

    public LogInInputData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    String getEmail() {
        return email;
    }

    String getPassword() {
        return password;
    }
}
