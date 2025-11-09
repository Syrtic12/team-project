package entity;

public class TeamMember extends User {

    public TeamMember(String name, String email, String role, String password) {
        super(name, email, role, password);
    }

    public void leaveTeam() {}

    //add utility methods for team members here, like view all tasks assigned to them fpr some team
}
