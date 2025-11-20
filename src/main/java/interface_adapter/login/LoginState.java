package interface_adapter.login;

public class LoginState {
    private String email = "";
    private String processError;
    private String password = "";

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getLoginError() {
        return this.processError;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setError(String error) {
        this.processError = error;
    }

    public String getError() {
        return this.processError;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
