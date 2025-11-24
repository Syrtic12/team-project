package use_case.logged_in;

import java.util.ArrayList;
import java.util.List;

public class LoggedInInputData {
    private final String email;
    private final String password;
    private final String processError;
    private final List<String> teams;

    public LoggedInInputData(String email, String password, String processError, List<String> teams) {
        this.email = email;
        this.password = password;
        this.processError = processError;
        this.teams = teams;
    }

    String getEmail() {return email;}

    String getPassword() {return password;}

    String getProcessError() {return processError;}

    List<String> getTeams() {return teams;}
}
