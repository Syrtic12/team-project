package interface_adapter.logged_in;

public class LoggedInState {
    private String email = "";
    private String password = "";
    private String passwordError;

    public LoggedInState (LoggedInState copy) {
        email = copy.email;
        password = copy.password;
        passwordError = copy.passwordError;
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
}
