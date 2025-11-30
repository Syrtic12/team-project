package use_case.signup;

public class LeaveTeamInputData {
    private final String email;
    private final String name;
    private final String password;
    private final String repeatPassword;

    public LeaveTeamInputData(String email, String name, String password, String repeatPassword) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    String getEmail() {
        return email;
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
