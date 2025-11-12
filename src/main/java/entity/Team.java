package entity;
import java.util.List;
import java.util.ArrayList;

//essentially tasklist for a team
public class Team {
    private List<String> users;
    private TeamLeader leader;
    private List<Task> tasks;

    public Team(TeamLeader leader){
        this.leader = leader;
        this.users = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    public List<Task> GetTasks(){
        return this.tasks;
    }

    public void addTask(){}
    public void removeTask(){}
}
