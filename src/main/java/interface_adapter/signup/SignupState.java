package interface_adapter.signup;

public class SignupState {
    private String username = "";
    private String usernameError = "";
    private String password = "";
    private String passwordError = "";
    private String role = "";
    private String roleError = "";
    private String repeatPassword = "";
    private String repeatPasswordError = "";
    private String email = "";
    private String emailError = "";

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public Object getUsernameError() {
        return usernameError;
    }

    public Object getEmailError() {
        return emailError;
    }

    public Object getRoleError() {
        return roleError;
    }

    public Object getPasswordError() {
        return passwordError;
    }

    public Object getRepeatPasswordError() {
        return repeatPasswordError;
    }

    public void setUsernameError(String username) {
        this.usernameError = usernameError;
    }

    public void setEmailError(String email) {
        this.emailError = emailError;
    }

    public void setRoleError(String role) {
        this.roleError = roleError;
    }

    public void setPasswordError(String password) {
        this.passwordError = passwordError;
    }

    public void setRepeatPasswordError(String repeatPassword) {
        this.repeatPasswordError = repeatPasswordError;
    }

    @Override
    public String toString() {
        return "SignupState{"
                + "username='" + username + '\''
                + ", password='" + password + '\''
                + ", repeatPassword='" + repeatPassword + '\''
                + ", email='" + email + '\''
                + '}';
    }
}
