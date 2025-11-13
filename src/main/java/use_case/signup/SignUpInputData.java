package use_case.signup;

public class SignUpInputData {
    private final String email;
    private final String name;
    private final String role;
    private final String password;
    private final String repeatPassword;

    public SignUpInputData(String email, String name, String role, String password, String repeatPassword) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    String getEmail() {
        return email;
    }

    String getRole() {
        return role;
    }

    String getName() {
        return name;
    }

    String getPassword() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }
}
