package entity;

public class TeamLeader extends User {
    // maybe add team leader specific attributes later, like Teams led
    public TeamLeader(String name, String email, String role, String password) {
        super(name, email, role, password);
    }

    public void addUser() {}
    public void removeUser() {}
    public void createTeam() {}
    public void disbandTeam() {}

}
