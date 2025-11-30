package use_case.team;

import java.util.List;

public interface TeamOutputBoundary {
    /**
     * Prepares the success view.
     * @param outputData the output data
     */
    void prepareSuccessView(TeamOutputData outputData);

    /**
     * Prepares the failure view.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    void switchToLoggedInView();

    void switchToManageTeamView(List<String> teamMembers, String teamID);

    void switchToCreateTaskView();

    void switchToEditTaskView(String taskId, String teamId, Integer status, String title, String description);
}
