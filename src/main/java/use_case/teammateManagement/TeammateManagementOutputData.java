package use_case.teammateManagement;

import java.util.List;

public class TeammateManagementOutputData {
    private final boolean success;
    private final String teamId;
    private final String userId;
    private final List<String> memberList;

    public TeammateManagementOutputData(boolean success, String teamId, String userId, List<String> memberList) {
        this.success = success;
        this.teamId = teamId;
        this.userId = userId;
        this.memberList = memberList;
    }

    public boolean isSuccess() { return success; }

    public List<String> getMemberList() { return memberList; }

    public String getUserId() { return userId; }

    public String getTeamId() { return teamId; }
}
