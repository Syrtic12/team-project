package use_case.signup;
/**
 * Output Data for the Signup Use Case.
 */
public class SignUpOutputData {

    private final String idx;
    private final String email;

    public SignUpOutputData(String idx ,String email) {
        this.idx = idx;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getIdx() {
        return idx;
    }

}