package usecase.team;
import java.util.Map;

public class TeamInputData {
    private final String teamId;
    private final Map<String, String> NotStartedTasks;
    private final Map<String, String> InProgressTasks;
    private final Map<String, String> CompletedTasks;

    public TeamInputData(String teamId, Map<String, String> NotStartedTasks, Map<String, String> InProgressTasks,
                         Map<String, String> CompletedTasks) {
        this.teamId = teamId;
        this.NotStartedTasks = NotStartedTasks;
        this.InProgressTasks = InProgressTasks;
        this.CompletedTasks = CompletedTasks;
    }

    String getTeamId() {return teamId;}
    Map<String,String> getNotStartedTasks(){return NotStartedTasks;}
    Map<String,String> getInProgressTasks(){return InProgressTasks;}
    Map<String,String> getCompletedTasks(){return CompletedTasks;}

}
