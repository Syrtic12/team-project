package use_case.create_team;

public class CreateTeamOutputData {

    private final String teamName;
    private final String leaderName;

    public CreateTeamOutputData(String teamName, String leaderName) {
        this.teamName = teamName;
        this.leaderName = leaderName;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getLeaderName() {
        return leaderName;
    }
}

