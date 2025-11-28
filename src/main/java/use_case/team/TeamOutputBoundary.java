package use_case.team;

import use_case.teammateManagement.TeammateManagementOutputData;

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

    void switchToManageTeamView();

    void switchToCreateTaskView();

    void switchToEditTaskView(String taskId, String teamId, Integer status);
}
