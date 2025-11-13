package entity;

public class TeamLeader extends User {
    /**
     * @param name name of the TeamLeader
     * @param email email of the TeamLeader
     * @param role role of the TeamLeader, should be "Leader"
     * @param password
     * Constructor for TeamLeader
     */
    // maybe add team leader specific attributes later, like Teams led
    public TeamLeader(String name, String email, String role, String password) {
        super(name, email, role, password);
    }

    public void addUser() {}
    public void removeUser() {}
    public void createTeam() {}
    public void disbandTeam() {}

}
